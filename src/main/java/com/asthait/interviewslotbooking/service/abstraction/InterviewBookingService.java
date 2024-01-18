package com.asthait.interviewslotbooking.service.abstraction;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CancelBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.request.CreateSlotRequestDTO;
import com.asthait.interviewslotbooking.dto.request.UpdateBookingRequestDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface InterviewBookingService {

    void createBookingSlot(@Valid CreateSlotRequestDTO createSlotRequestDTO);

    void bookSlot(@Valid BookingRequestDTO bookingRequest);

    void cancelBooking(@Valid CancelBookingRequestDTO cancelBookingRequest);

    void updateBooking(@Valid UpdateBookingRequestDTO updateBookingRequest);

    List<SlotResponseDTO> getAllSlotsWithDateTime(LocalDateTime startTime, LocalDateTime endTime);
}
