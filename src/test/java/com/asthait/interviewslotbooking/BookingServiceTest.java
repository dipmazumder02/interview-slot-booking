package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.dto.request.UpdateBookingRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.InterviewBookingSlot;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private InterviewBookingSlotRepository interviewBookingSlotRepository;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void updateBooking_InvalidSlotId_ThrowsException() {
        // Mock
        UpdateBookingRequestDTO updateBookingRequestDTO = new UpdateBookingRequestDTO();
        when(interviewBookingSlotRepository.findById(any(Long.class))).thenReturn(Optional.of(new InterviewBookingSlot()));

        // Prepare the request DTO
        updateBookingRequestDTO.setInterviewBookingSlotId(1L);
        updateBookingRequestDTO.setSlotId(1L);
        updateBookingRequestDTO.setInterviewerId(1L);
        updateBookingRequestDTO.setAgenda("Test");

        // Test and Verify
        assertThrows(BookingException.class, () -> bookingService.updateBooking(updateBookingRequestDTO));
        verify(slotRepository, never()).save(any(Slot.class)); // Slot should not be saved
        verify(interviewBookingSlotRepository, never()).save(any(InterviewBookingSlot.class));
    }
}