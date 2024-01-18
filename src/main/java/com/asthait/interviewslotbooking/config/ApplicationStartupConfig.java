package com.asthait.interviewslotbooking.config;

import com.asthait.interviewslotbooking.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


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
