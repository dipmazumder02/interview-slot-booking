package com.asthait.interviewslotbooking.dto.response;

import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotResponseDTO {

    private Long slotId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BookingSlotStatus status;

    private InterviewDetailsDTO interviewDetails;
}
