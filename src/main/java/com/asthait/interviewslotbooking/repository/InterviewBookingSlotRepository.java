package com.asthait.interviewslotbooking.repository;

import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewBookingSlotRepository extends JpaRepository<InterviewBookingSlot, Long> {
    List<InterviewBookingSlot> findByInterviewerIdAndSlotId(Long interviewerId, Long slotId);
    InterviewBookingSlot findBySlotId(Long slotId);
}