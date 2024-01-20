package com.asthait.interviewslotbooking.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object for creating a booking request.
 */
@NoArgsConstructor
@Getter
@Setter
public class BookingRequestDTO {

    @NotNull(message = "Interviewer ID cannot be null")
    private Long interviewerId;

    @NotNull(message = "Slot ID cannot be null")
    private Long slotId;

    @NotNull(message = "Agenda cannot be null")
    private String agenda;
    @NotNull(message = "City cannot be null")
    private String city;
}
