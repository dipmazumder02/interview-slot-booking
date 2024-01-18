package com.asthait.interviewslotbooking;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.repository.InterviewerRepository;
import com.asthait.interviewslotbooking.service.InterviewerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewerServiceTest {

    @Mock
    private InterviewerRepository interviewerRepository;

    @InjectMocks
    private InterviewerService interviewerService;

    @Test
    void prepopulateInterviewers() {
        when(interviewerRepository.count()).thenReturn(0L);

        assertDoesNotThrow(() -> interviewerService.prepopulateInterviewers());
    }

    @Test
    void getInterviewerById() {
        Long interviewerId = 1L;
        Interviewer interviewer = new Interviewer();
        interviewer.setId(interviewerId);

        when(interviewerRepository.findById(interviewerId)).thenReturn(java.util.Optional.of(interviewer));

        assertEquals(interviewer, interviewerService.getInterviewerById(interviewerId));
    }

    @Test
    void getInterviewerByIdThrowsExceptionWhenInterviewerNotFound() {
        Long interviewerId = 1L;

        when(interviewerRepository.findById(interviewerId)).thenReturn(java.util.Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> interviewerService.getInterviewerById(interviewerId));
        assertEquals("Invalid interviewer ID", exception.getMessage());
    }
}