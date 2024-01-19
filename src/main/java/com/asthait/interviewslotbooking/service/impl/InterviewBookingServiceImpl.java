package com.asthait.interviewslotbooking.service.impl;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CancelBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CreateSlotRequestDTO;
import com.asthait.interviewslotbooking.dto.request.UpdateBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.response.InterviewDetailsDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.InterviewerRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.BookingService;
import com.asthait.interviewslotbooking.service.SlotService;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewBookingServiceImpl implements InterviewBookingService {

    private final SlotRepository slotRepository;
    private final InterviewerRepository interviewerRepository;
    private final InterviewBookingSlotRepository interviewBookingSlotRepository;
    private final SlotService slotService;
    private final BookingService bookingService;

    @Override
    public Slot createBookingSlot(@Valid CreateSlotRequestDTO createSlotRequestDTO) {
        return slotService.createSlot(createSlotRequestDTO);
    }

    @Override
    public void bookSlot(@Valid BookingRequestDTO bookingRequest) {
        bookingService.bookSlot(bookingRequest);
    }

    @Override
    public List<SlotResponseDTO> getAllSlotsWithDateTime(LocalDateTime startTime, LocalDateTime endTime) {

        List<Slot> slots = slotRepository.findByStartTimeBetween(startTime, endTime);
        return slots.stream().map(this::convertToSlotResponseDTO).collect(Collectors.toList());
    }


    public SlotResponseDTO convertToSlotResponseDTO(Slot slot) {
        SlotResponseDTO responseDTO = new SlotResponseDTO();
        responseDTO.setSlotId(slot.getId());
        responseDTO.setStartTime(slot.getStartTime());
        responseDTO.setEndTime(slot.getEndTime());
        responseDTO.setStatus(slot.getStatus());

        // Check if the slot is booked
        if (slot.getStatus() == BookingSlotStatus.BOOKED) {
            InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findBySlotId(slot.getId());
            if (bookingSlot != null) {
                InterviewDetailsDTO interviewDetailsDTO = new InterviewDetailsDTO(bookingSlot.getId(), bookingSlot.getInterviewer().getId(), bookingSlot.getInterviewer().getName(), bookingSlot.getAgenda(), bookingSlot.getWeatherInformation());
                responseDTO.setInterviewDetails(interviewDetailsDTO);
            }
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public void cancelBooking(@Valid CancelBookingRequestDTO cancelBookingRequest) {
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
        deleteBookingSlot(bookingSlot);
    }

    @Override
    @Transactional
    public void updateBooking(@Valid UpdateBookingRequestDTO updateBookingRequest) {
        InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findById(updateBookingRequest.getInterviewBookingSlotId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_INTERVIEW_BOOKING_SLOT_ID));
        Slot previousSlot = bookingSlot.getSlot();
        Slot newSlot = slotRepository.findById(updateBookingRequest.getSlotId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_SLOT_ID));

        Interviewer newInterviewer = interviewerRepository.findById(updateBookingRequest.getInterviewerId()).orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_INTERVIEWER_ID));

        previousSlot.setStatus(BookingSlotStatus.AVAILABLE);
        slotRepository.save(previousSlot);

        newSlot.setStatus(BookingSlotStatus.BOOKED);
        newSlot = slotRepository.save(newSlot);

        bookingSlot.setSlot(newSlot);
        bookingSlot.setInterviewer(newInterviewer);
        bookingSlot.setAgenda(updateBookingRequest.getAgenda());
        interviewBookingSlotRepository.save(bookingSlot);
    }

    @Transactional
    public void deleteBookingSlot(InterviewBookingSlot bookingSlot) {
        interviewBookingSlotRepository.delete(bookingSlot);
    }
}
