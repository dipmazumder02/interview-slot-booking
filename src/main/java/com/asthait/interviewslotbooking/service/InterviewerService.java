package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.repository.InterviewerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewerService {

    private final InterviewerRepository interviewerRepository;
    public void prepopulateInterviewers() {
        if (interviewerRepository.count() == 0) {
            Interviewer interviewer1 = new Interviewer();
            interviewer1.setName("John Doe");
            interviewerRepository.save(interviewer1);

            Interviewer interviewer2 = new Interviewer();
            interviewer2.setName("Jane Doe");
            interviewerRepository.save(interviewer2);

            Interviewer interviewer3 = new Interviewer();
            interviewer3.setName("Jean Doe");
            interviewerRepository.save(interviewer3);
        }
    }
    public Interviewer getInterviewerById(Long interviewerId) {
        return interviewerRepository.findById(interviewerId)
                .orElseThrow(() -> new BookingException("Invalid interviewer ID"));
    }

}
