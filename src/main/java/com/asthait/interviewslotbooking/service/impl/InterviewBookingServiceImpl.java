package com.asthait.interviewslotbooking.service.impl;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CancelBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CreateSlotRequestDTO;
import com.asthait.interviewslotbooking.dto.request.UpdateBookingRequestDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void createBookingSlot(@Valid CreateSlotRequestDTO createSlotRequestDTO) {
        slotService.createSlot(createSlotRequestDTO);
    }

    @Override
    public void bookSlot(@Valid BookingRequestDTO bookingRequest) {
        bookingService.bookSlot(bookingRequest);
    }

    @Override
    public List<SlotResponseDTO> getAllSlotsWithDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        List<Slot> slots = slotRepository.findByStartTimeBetween(startTime, endTime);
        return slots.stream()
                .map(this::convertToSlotResponseDTO)
                .collect(Collectors.toList());
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
                responseDTO.setInterviewerId(bookingSlot.getInterviewer().getId());
                responseDTO.setInterviewerName(bookingSlot.getInterviewer().getName());
            }
        }
        return responseDTO;
    }

    @Override
    public void cancelBooking(@Valid CancelBookingRequestDTO cancelBookingRequest) {
        Long interviewBookingSlotId = cancelBookingRequest.getInterviewBookingSlotId();

        // Check if the provided interviewBookingSlotId is valid
        InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findById(interviewBookingSlotId)
                .orElseThrow(() -> new BookingException("Invalid interview booking slot ID"));

        // Check if the booking exists
        if (bookingSlot == null) {
            throw new BookingException("Booking not found for the provided ID");
        }

        // Remove the entity from the database
        interviewBookingSlotRepository.delete(bookingSlot);
    }

    @Override
    public void updateBooking(@Valid UpdateBookingRequestDTO updateBookingRequest) {
        InterviewBookingSlot bookingSlot = interviewBookingSlotRepository.findById(updateBookingRequest.getInterviewBookingSlotId())
                .orElseThrow(() -> new BookingException("Invalid interview booking slot ID"));

        // Check if the booking exists
        if (bookingSlot == null) {
            throw new BookingException("Booking not found for the provided ID");
        }

        // Update the booking details
        Slot newSlot = slotRepository.findById(updateBookingRequest.getSlotId())
                .orElseThrow(() -> new BookingException("Invalid new slot ID"));

        Interviewer newInterviewer = interviewerRepository.findById(updateBookingRequest.getInterviewerId())
                .orElseThrow(() -> new BookingException("Invalid new interviewer ID"));

        bookingSlot.setSlot(newSlot);
        bookingSlot.setInterviewer(newInterviewer);
        bookingSlot.setAgenda(updateBookingRequest.getAgenda());
        interviewBookingSlotRepository.save(bookingSlot);
    }
}
