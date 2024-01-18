package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final InterviewerService interviewerService;
    private final SlotService slotService;
    private final SlotRepository slotRepository;
    private final InterviewBookingSlotRepository interviewBookingSlotRepository;

    public void bookSlot(BookingRequestDTO bookingRequest) {
        // Validate if the interviewer and slot combination is unique
        validateInterviewerAndSlot(bookingRequest.getInterviewerId(), bookingRequest.getSlotId());

        Slot slot = slotService.getSlotBySlotId(bookingRequest.getSlotId());
        // Check if the slot is available
        if (slot.getStatus() == BookingSlotStatus.BOOKED) {
            throw new BookingException("The specified slot is already booked");
        }

        Interviewer interviewer = interviewerService.getInterviewerById(bookingRequest.getInterviewerId());

        // Create booking slot entity
        InterviewBookingSlot bookingSlot = new InterviewBookingSlot();
        bookingSlot.setInterviewer(interviewer);
        bookingSlot.setSlot(slot);
        bookingSlot.setAgenda(bookingRequest.getAgenda());

        // Update slot status to BOOKED
        slot.setStatus(BookingSlotStatus.BOOKED);
        slotRepository.save(slot);

        // Save booking slot
        interviewBookingSlotRepository.save(bookingSlot);

    }

    private void validateInterviewerAndSlot(Long interviewerId, Long slotId) {
        List<InterviewBookingSlot> existingBookings = interviewBookingSlotRepository.findByInterviewerIdAndSlotId(interviewerId, slotId);
        if (!existingBookings.isEmpty()) {
            throw new BookingException("This slot is already booked by the specified interviewer");
        }
    }

}