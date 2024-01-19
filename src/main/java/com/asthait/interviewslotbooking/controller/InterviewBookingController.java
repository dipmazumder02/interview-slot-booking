package com.asthait.interviewslotbooking.controller;

import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.dto.response.ResponseDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.util.ResponseMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewBookingController {

    private final InterviewBookingService interviewBookingService;

    @PostMapping("/create-slot")
    public ResponseEntity<ResponseDTO<Slot>> createBookingSlot(@Valid @RequestBody CreateSlotRequestDTO createSlotRequestDTO) {
        Slot savedSlot = interviewBookingService.createBookingSlot(createSlotRequestDTO);
        return ResponseEntity.ok(ResponseDTO.success(savedSlot,ResponseMessageUtil.BOOKING_SLOT_CREATED));
    }

    @PostMapping("/book-slot")
    public ResponseEntity<ResponseDTO<String>> bookSlot(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        interviewBookingService.bookSlot(bookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.SLOT_BOOKED));
    }


    @PostMapping("/cancel-booking")
    public ResponseEntity<ResponseDTO<String>> cancelBooking(@Valid @RequestBody CancelBookingRequestDTO cancelBookingRequest) {
        interviewBookingService.cancelBooking(cancelBookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_CANCELED));
    }


    @PostMapping("/update-booking")
    public ResponseEntity<ResponseDTO<String>> updateBooking(@Valid @RequestBody UpdateBookingRequestDTO updateBookingRequest) {
        interviewBookingService.updateBooking(updateBookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_UPDATED));
    }

    @GetMapping("/all-slots")
    public ResponseEntity<ResponseDTO<List<SlotResponseDTO>>> getAllSlotsWithDateTime(@Valid @RequestBody AllSlotsRequestDTO requestDTO) {
        List<SlotResponseDTO> slots = interviewBookingService.getAllSlotsWithDateTime(requestDTO.getStartTime(), requestDTO.getEndTime());
        return ResponseEntity.ok(ResponseDTO.success(slots, ResponseMessageUtil.OPERATION_SUCCESSFUL));
    }
}
