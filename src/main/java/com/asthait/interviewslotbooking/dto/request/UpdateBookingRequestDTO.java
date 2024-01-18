package com.asthait.interviewslotbooking.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object for updating a booking request.
 */
@Data
public class UpdateBookingRequestDTO extends BookingRequestDTO{
    @NotNull(message = "Interviewer Booking Slot ID cannot be null")
    private Long interviewBookingSlotId;
}
