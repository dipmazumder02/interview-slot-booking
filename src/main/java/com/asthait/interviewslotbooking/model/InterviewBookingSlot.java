package com.asthait.interviewslotbooking.model;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * Entity class representing a booked interview slot.
 */
@Data
@Entity
public class InterviewBookingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;

    private String weatherInformation;
    @NotBlank(message = "Agenda cannot be blank")
    private String agenda;


}