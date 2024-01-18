package com.asthait.interviewslotbooking.dto.request;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating a booking request.
 */
@Data
public class BookingRequestDTO {

    @NotNull(message = "Interviewer ID cannot be null")
    private Long interviewerId;

    @NotNull(message = "Slot ID cannot be null")
    private Long slotId;

    @NotNull(message = "Agenda cannot be null")
    private String agenda;
}
