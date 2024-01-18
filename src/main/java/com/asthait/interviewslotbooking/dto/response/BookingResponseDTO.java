package com.asthait.interviewslotbooking.dto.response;

import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private BookingSlotStatus status;
}
