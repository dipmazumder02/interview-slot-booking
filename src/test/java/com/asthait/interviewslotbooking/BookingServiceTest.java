package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.dto.request.BookingRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Interviewer;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.BookingService;
import com.asthait.interviewslotbooking.service.InterviewerService;
import com.asthait.interviewslotbooking.service.SlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private InterviewerService interviewerService;

    @Mock
    private SlotService slotService;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private InterviewBookingSlotRepository interviewBookingSlotRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void bookSlot_ValidBookingRequest_SavesBookingSlotAndUpdatesSlotStatus() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setInterviewerId(1L);
        bookingRequest.setSlotId(2L);
        bookingRequest.setAgenda("Interview agenda");

        Interviewer mockInterviewer = new Interviewer();
        mockInterviewer.setId(1L);
        mockInterviewer.setName("John Doe");

        when(interviewerService.getInterviewerById(bookingRequest.getInterviewerId())).thenReturn(mockInterviewer);

        Slot mockSlot = new Slot();
        mockSlot.setId(2L);
        mockSlot.setStatus(BookingSlotStatus.AVAILABLE);

        when(slotService.getSlotBySlotId(bookingRequest.getSlotId())).thenReturn(mockSlot);

        InterviewBookingSlot mockBookingSlot = new InterviewBookingSlot();
        mockBookingSlot.setInterviewer(mockInterviewer);
        mockBookingSlot.setSlot(mockSlot);
        mockBookingSlot.setAgenda(bookingRequest.getAgenda());

        when(interviewBookingSlotRepository.save(any(InterviewBookingSlot.class))).thenReturn(mockBookingSlot);

        bookingService.bookSlot(bookingRequest);

        assertEquals(BookingSlotStatus.BOOKED, mockSlot.getStatus());
        verify(slotRepository).save(mockSlot);
        verify(interviewBookingSlotRepository).save(mockBookingSlot);
    }

    @Test
    void bookSlot_InvalidSlotStatus_ThrowsBookingException() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setSlotId(1L);

        Slot mockSlot = new Slot();
        mockSlot.setId(1L);
        mockSlot.setStatus(BookingSlotStatus.BOOKED);

        when(slotService.getSlotBySlotId(bookingRequest.getSlotId())).thenReturn(mockSlot);

        assertThrows(BookingException.class, () -> bookingService.bookSlot(bookingRequest));
    }
    @Test
    void bookSlot_DuplicateBooking_ThrowsBookingException() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setInterviewerId(1L);
        bookingRequest.setSlotId(2L);

        Interviewer mockInterviewer = new Interviewer();
        mockInterviewer.setId(1L);
        mockInterviewer.setName("John Doe");

        Slot mockSlot = new Slot();
        mockSlot.setId(2L);
        mockSlot.setStatus(BookingSlotStatus.BOOKED);

        when(slotService.getSlotBySlotId(bookingRequest.getSlotId())).thenReturn(mockSlot);

        InterviewBookingSlot existingBooking = new InterviewBookingSlot();
        existingBooking.setInterviewer(mockInterviewer);
        existingBooking.setSlot(mockSlot);

        assertThrows(BookingException.class, () -> bookingService.bookSlot(bookingRequest));
    }


}
