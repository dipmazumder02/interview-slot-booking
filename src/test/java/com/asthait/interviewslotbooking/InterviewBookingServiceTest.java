package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.*;
import com.asthait.interviewslotbooking.repository.*;
import com.asthait.interviewslotbooking.service.*;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.service.impl.InterviewBookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterviewBookingServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private InterviewBookingSlotRepository interviewBookingSlotRepository;

    @Mock
    private SlotService slotService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private InterviewBookingServiceImpl interviewBookingService;

    @Test
    void createBookingSlot_ValidRequest_CallsSlotService() {
        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        interviewBookingService.createBookingSlot(createSlotRequestDTO);

        verify(slotService).createSlot(createSlotRequestDTO);
    }

    @Test
    void bookSlot_ValidBookingRequest_CallsBookingService() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        interviewBookingService.bookSlot(bookingRequest);

        verify(bookingService).bookSlot(bookingRequest);
    }

    @Test
    void getAllSlotsWithDateTime_ReturnsListOfSlotResponseDTO() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        Slot slot1 = new Slot();
        slot1.setId(1L);
        slot1.setStartTime(startTime);
        slot1.setEndTime(endTime);
        slot1.setStatus(BookingSlotStatus.AVAILABLE);

        Slot slot2 = new Slot();
        slot2.setId(2L);
        slot2.setStartTime(startTime.plusHours(2));
        slot2.setEndTime(endTime.plusHours(2));
        slot2.setStatus(BookingSlotStatus.BOOKED);

        when(slotRepository.findByStartTimeBetween(startTime, endTime))
                .thenReturn(Arrays.asList(slot1, slot2));

        List<SlotResponseDTO> result = interviewBookingService.getAllSlotsWithDateTime(startTime, endTime);

        assertEquals(2, result.size());
        assertEquals(slot1.getId(), result.get(0).getSlotId());
        assertEquals(slot2.getId(), result.get(1).getSlotId());
    }

    @Test
    void cancelBooking_ValidCancelBookingRequest_DeletesBookingSlot() {
        CancelBookingRequestDTO cancelBookingRequest = new CancelBookingRequestDTO();
        cancelBookingRequest.setInterviewBookingSlotId(1L);

        InterviewBookingSlot bookingSlot = new InterviewBookingSlot();
        Slot slot = new Slot();
        slot.setId(1L);
        slot.setStatus(BookingSlotStatus.AVAILABLE);
        bookingSlot.setSlot(slot);
        when(interviewBookingSlotRepository.findById(cancelBookingRequest.getInterviewBookingSlotId()))
                .thenReturn(Optional.of(bookingSlot));

        interviewBookingService.cancelBooking(cancelBookingRequest);

        verify(interviewBookingSlotRepository).delete(bookingSlot);
    }

    @Test
    void cancelBooking_InvalidCancelBookingRequest_ThrowsBookingException() {
        CancelBookingRequestDTO cancelBookingRequest = new CancelBookingRequestDTO();
        cancelBookingRequest.setInterviewBookingSlotId(1L);

        when(interviewBookingSlotRepository.findById(cancelBookingRequest.getInterviewBookingSlotId()))
                .thenReturn(Optional.empty());

        assertThrows(BookingException.class, () -> interviewBookingService.cancelBooking(cancelBookingRequest));
    }

    // Add more tests for the remaining methods as needed.
}
