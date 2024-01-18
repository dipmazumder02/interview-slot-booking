package com.asthait.interviewslotbooking.config;

import com.asthait.interviewslotbooking.service.InterviewerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationStartupConfig {

    private final InterviewerService interviewerService;

    @Autowired
    public ApplicationStartupConfig(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @PostConstruct
    public void prepopulateData() {
        interviewerService.prepopulateInterviewers();
    }
}
