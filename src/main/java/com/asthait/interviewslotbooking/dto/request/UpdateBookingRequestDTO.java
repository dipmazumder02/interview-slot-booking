package com.asthait.interviewslotbooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for updating a booking request.
 */
@Data
public class UpdateBookingRequestDTO extends BookingRequestDTO{
    @NotNull(message = "Interviewer Booking Slot ID cannot be null")
    private Long interviewBookingSlotId;
}
