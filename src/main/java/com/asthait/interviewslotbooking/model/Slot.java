package com.asthait.interviewslotbooking.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * Entity class representing a booking slot.
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"start_time", "end_time"}))
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingSlotStatus status;


    @Version
    private Long version;

}

