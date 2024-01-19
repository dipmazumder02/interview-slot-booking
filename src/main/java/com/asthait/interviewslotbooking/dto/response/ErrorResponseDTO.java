package com.asthait.interviewslotbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponseDTO {

    private String message;
    private boolean success;
}