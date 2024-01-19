package com.asthait.interviewslotbooking.dto.request;

// CreateBookingSlotDTO.java
import com.asthait.interviewslotbooking.desrializer.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateSlotRequestDTO {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @FutureOrPresent(message = "Start time must be in the present or future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @FutureOrPresent(message = "End time must be in the present or future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;


}
