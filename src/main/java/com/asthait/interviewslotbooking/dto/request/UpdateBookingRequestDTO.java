package com.asthait.interviewslotbooking.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object for updating a booking request.
 */
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookingRequestDTO extends BookingRequestDTO{
    @NotNull(message = "Interviewer Booking Slot ID cannot be null")
    private Long interviewBookingSlotId;
}
