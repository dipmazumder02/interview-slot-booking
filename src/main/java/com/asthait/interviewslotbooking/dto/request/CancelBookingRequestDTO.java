package com.asthait.interviewslotbooking.dto.request;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelBookingRequestDTO {

    @NotNull
    private Long interviewBookingSlotId;
}
