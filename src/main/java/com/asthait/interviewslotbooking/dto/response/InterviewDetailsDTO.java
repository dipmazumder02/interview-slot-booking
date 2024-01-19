package com.asthait.interviewslotbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterviewDetailsDTO {
    private Long interviewBookingSlotId; // Set only if status is BOOKED
    private Long interviewerId; // Set only if status is BOOKED
    private String interviewerName; // Set only if status is BOOKED

    private String agenda;
    private String weatherInfo;
}
