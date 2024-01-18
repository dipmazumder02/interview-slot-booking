package com.asthait.interviewslotbooking.repository;

import com.asthait.interviewslotbooking.model.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {
}