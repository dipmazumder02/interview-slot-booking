package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
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
    private final SlotRepository slotRepository;
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


    private void validateInterviewerAndSlot(Long interviewerId, Long slotId) {
        List<InterviewBookingSlot> existingBookings = interviewBookingSlotRepository.findByInterviewerIdAndSlotId(interviewerId, slotId);
        if (!existingBookings.isEmpty()) {
            throw new BookingException(ExceptionMessageUtil.SLOT_ALREADY_BOOKED_BY_INTERVIEWER);
        }
    }

}