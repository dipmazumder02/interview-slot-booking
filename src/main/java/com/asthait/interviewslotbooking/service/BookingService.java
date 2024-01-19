package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CancelBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.UpdateBookingRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.InterviewerRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final InterviewerService interviewerService;
    private final SlotService slotService;
    private final WeatherService weatherService;
    private final SlotRepository slotRepository;
    private final InterviewerRepository interviewerRepository;
    private final InterviewBookingSlotRepository interviewBookingSlotRepository;
    private static final int MAX_RETRY_ATTEMPTS = 3;

    @Transactional
    public void bookSlot(BookingRequestDTO bookingRequest) {
        int attempt = 0;
        boolean success = false;

        while (!success && attempt < MAX_RETRY_ATTEMPTS) {
            try {
                // Validate if the interviewer and slot combination is unique
                validateInterviewerAndSlot(bookingRequest.getInterviewerId(), bookingRequest.getSlotId());

                Slot slot = slotService.getSlotBySlotId(bookingRequest.getSlotId());

                // Check if the slot is available
                if (slot.getStatus() == BookingSlotStatus.BOOKED) {
                    throw new BookingException(ExceptionMessageUtil.SLOT_ALREADY_BOOKED);
                }

                Interviewer interviewer = interviewerService.getInterviewerById(bookingRequest.getInterviewerId());


                // Fetch weather information based on the interview location
                String interviewLocation = bookingRequest.getCity();
                String weatherInformation = weatherService.getWeatherInformation(interviewLocation);

                // Create booking slot entity
                InterviewBookingSlot bookingSlot = new InterviewBookingSlot();
                bookingSlot.setInterviewer(interviewer);
                bookingSlot.setSlot(slot);
                bookingSlot.setAgenda(bookingRequest.getAgenda());
                bookingSlot.setWeatherInformation(weatherInformation);


                // Update slot status to BOOKED
                slot.setStatus(BookingSlotStatus.BOOKED);
                slotRepository.save(slot);

                // Save booking slot
                interviewBookingSlotRepository.save(bookingSlot);

                success = true; // Mark the attempt as successful
            } catch (OptimisticLockException e) {
                // Handle optimistic lock exception (e.g., retry or notify the user)
                attempt++;
            }
        }

        if (!success) {
            throw new BookingException(ExceptionMessageUtil.FAILED_TO_BOOK_SLOT);
        }
    }

    @Transactional
    public void cancelBooking(CancelBookingRequestDTO cancelBookingRequest){
        Long interviewBookingSlotId = cancelBookingRequest.getInterviewBookingSlotId();

        // Check if the provided interviewBookingSlotId is valid
        InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findById(interviewBookingSlotId).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_INTERVIEW_BOOKING_SLOT_ID));
        // Check if the booking exists
        if (bookingSlot == null) {
            throw new BookingException(ExceptionMessageUtil.BOOKING_NOT_FOUND);
        }
        Slot slot = bookingSlot.getSlot();
        slot.setStatus(BookingSlotStatus.AVAILABLE);
        slotRepository.save(slot);
        interviewBookingSlotRepository.delete(bookingSlot);

    }
    @Transactional
    public void updateBooking(UpdateBookingRequestDTO updateBookingRequest){
        InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findById(updateBookingRequest.getInterviewBookingSlotId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_INTERVIEW_BOOKING_SLOT_ID));
        Slot previousSlot = bookingSlot.getSlot();
        if (previousSlot != null) {
            // Update the status of the previous slot
            previousSlot.setStatus(BookingSlotStatus.AVAILABLE);
            slotRepository.save(previousSlot);
        } else {
            // Handle the case where previousSlot is null, e.g., throw an exception or log a message
            throw new BookingException(ExceptionMessageUtil.PREVIOUS_SLOT_NULL);
        }

        Slot newSlot = slotRepository.findById(updateBookingRequest.getSlotId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_SLOT_ID));
        newSlot.setStatus(BookingSlotStatus.BOOKED);
        newSlot = slotRepository.save(newSlot);

        Interviewer newInterviewer = interviewerRepository.findById(updateBookingRequest.getInterviewerId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_INTERVIEWER_ID));


        bookingSlot.setSlot(newSlot);
        bookingSlot.setInterviewer(newInterviewer);
        bookingSlot.setAgenda(updateBookingRequest.getAgenda());
        interviewBookingSlotRepository.save(bookingSlot);

    }
    private void validateInterviewerAndSlot(Long interviewerId, Long slotId) {
        List<InterviewBookingSlot> existingBookings = interviewBookingSlotRepository.findByInterviewerIdAndSlotId(interviewerId, slotId);
        if (!existingBookings.isEmpty()) {
            throw new BookingException(ExceptionMessageUtil.SLOT_ALREADY_BOOKED_BY_INTERVIEWER);
        }
    }

}