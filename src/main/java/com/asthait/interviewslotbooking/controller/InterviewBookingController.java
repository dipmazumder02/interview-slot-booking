package com.asthait.interviewslotbooking.controller;

import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.dto.response.ResponseDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.util.ResponseMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
@Api(value = "Interview Booking API", description = "Operations related to interview slot booking")

public class InterviewBookingController {

    private final InterviewBookingService interviewBookingService;
    @ApiOperation(value = "Create a booking slot")
    @PostMapping("/create-slot")
    public ResponseEntity<ResponseDTO<Slot>> createBookingSlot(@Valid @RequestBody CreateSlotRequestDTO createSlotRequestDTO) {
        Slot savedSlot = interviewBookingService.createBookingSlot(createSlotRequestDTO);
        return ResponseEntity.ok(ResponseDTO.success(savedSlot,ResponseMessageUtil.BOOKING_SLOT_CREATED));
    }
    @ApiOperation(value = "Book a slot")
    @PostMapping("/book-slot")
    public ResponseEntity<ResponseDTO<String>> bookSlot(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        interviewBookingService.bookSlot(bookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.SLOT_BOOKED));
    }

    @ApiOperation(value = "Cancel a booking")
    @PostMapping("/cancel-booking")
    public ResponseEntity<ResponseDTO<String>> cancelBooking(@Valid @RequestBody CancelBookingRequestDTO cancelBookingRequest) {
        interviewBookingService.cancelBooking(cancelBookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_CANCELED));
    }

    @ApiOperation(value = "Update a booking")
    @PostMapping("/update-booking")
    public ResponseEntity<ResponseDTO<String>> updateBooking(@Valid @RequestBody UpdateBookingRequestDTO updateBookingRequest) {
        interviewBookingService.updateBooking(updateBookingRequest);
        return ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_UPDATED));
    }
    @ApiOperation(value = "Get all slots within a date range")
    @GetMapping("/all-slots")
    public ResponseEntity<ResponseDTO<List<SlotResponseDTO>>> getAllSlotsWithDateTime(@Valid @RequestBody AllSlotsRequestDTO requestDTO) {
        List<SlotResponseDTO> slots = interviewBookingService.getAllSlotsWithDateTime(requestDTO.getStartTime(), requestDTO.getEndTime());
        return ResponseEntity.ok(ResponseDTO.success(slots, ResponseMessageUtil.OPERATION_SUCCESSFUL));
    }
}
