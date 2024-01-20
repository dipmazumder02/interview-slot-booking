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
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.BookingService;
import com.asthait.interviewslotbooking.service.SlotService;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewBookingServiceImpl implements InterviewBookingService {

    private final SlotRepository slotRepository;
    private final InterviewBookingSlotRepository interviewBookingSlotRepository;
    private final SlotService slotService;
    private final BookingService bookingService;

    @Override
    public Slot createBookingSlot(@Valid CreateSlotRequestDTO createSlotRequestDTO) {
        log.info("Received request to create booking slot: {}", createSlotRequestDTO);
        return slotService.createSlot(createSlotRequestDTO);
    }

    @Override
    public void bookSlot(@Valid BookingRequestDTO bookingRequest) {
        log.info("Received request to book a slot: {}", bookingRequest);
        bookingService.bookSlot(bookingRequest);
        log.info("Slot booked successfully");
    }

    @Override
    public List<SlotResponseDTO> getAllSlotsWithDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("Received request to get all slots with date-time range: {} to {}", startTime, endTime);
        List<Slot> slots = slotRepository.findByStartTimeBetween(startTime, endTime);
        List<SlotResponseDTO> slotResponseDTOS = slots.stream().map(this::convertToSlotResponseDTO).collect(Collectors.toList());
        log.info("Returning slots: {}", slotResponseDTOS);
        return slotResponseDTOS;
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
    public void cancelBooking(@Valid CancelBookingRequestDTO cancelBookingRequest) throws BookingException{
        log.info("Received request to cancel booking: {}", cancelBookingRequest);
        bookingService.cancelBooking(cancelBookingRequest);
        log.info("Booking canceled successfully");
    }

    @Override
    public void updateBooking(@Valid UpdateBookingRequestDTO updateBookingRequest) {
        log.info("Received request to update booking: {}", updateBookingRequest);
        bookingService.updateBooking(updateBookingRequest);
        log.info("Booking updated successfully");
    }

}
