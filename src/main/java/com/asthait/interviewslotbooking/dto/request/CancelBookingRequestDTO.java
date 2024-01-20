package com.asthait.interviewslotbooking.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class CancelBookingRequestDTO {

    @NotNull
    private Long interviewBookingSlotId;
}
