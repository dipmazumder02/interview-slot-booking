package com.asthait.interviewslotbooking;
import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.InterviewBookingSlotRepository;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.BookingService;
import com.asthait.interviewslotbooking.service.SlotService;
import com.asthait.interviewslotbooking.service.impl.InterviewBookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InterviewBookingServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private InterviewBookingSlotRepository interviewBookingSlotRepository;

    @Mock
    private SlotService slotService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private InterviewBookingServiceImpl interviewBookingService;

    @Test
    public void createBookingSlot_ValidRequest_CallsSlotService() {
        // Mock
        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        when(slotService.createSlot(any(CreateSlotRequestDTO.class))).thenReturn(new Slot());

        // Test
        interviewBookingService.createBookingSlot(createSlotRequestDTO);

        // Verify
        verify(slotService, times(1)).createSlot(any(CreateSlotRequestDTO.class));
    }

    @Test
    public void bookSlot_ValidRequest_CallsBookingService() {
        // Mock
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        doNothing().when(bookingService).bookSlot(any(BookingRequestDTO.class));

        // Test
        interviewBookingService.bookSlot(bookingRequestDTO);

        // Verify
        verify(bookingService, times(1)).bookSlot(any(BookingRequestDTO.class));
    }

    @Test
    public void cancelBooking_ValidRequest_CallsBookingService() {
        // Mock
        CancelBookingRequestDTO cancelBookingRequestDTO = new CancelBookingRequestDTO();
        doNothing().when(bookingService).cancelBooking(any(CancelBookingRequestDTO.class));

        // Test
        interviewBookingService.cancelBooking(cancelBookingRequestDTO);

        // Verify
        verify(bookingService, times(1)).cancelBooking(any(CancelBookingRequestDTO.class));
    }

    @Test
    public void updateBooking_ValidRequest_CallsBookingService() {
        // Mock
        UpdateBookingRequestDTO updateBookingRequestDTO = new UpdateBookingRequestDTO();
        doNothing().when(bookingService).updateBooking(any(UpdateBookingRequestDTO.class));

        // Test
        interviewBookingService.updateBooking(updateBookingRequestDTO);

        // Verify
        verify(bookingService, times(1)).updateBooking(any(UpdateBookingRequestDTO.class));
    }

    @Test
    public void getAllSlotsWithDateTime_ValidRequest_CallsSlotRepository() {
        // Mock
        LocalDateTime startDateTime = LocalDateTime.now(); // Replace this with your desired start time
        LocalDateTime endDateTime = startDateTime.plusHours(1); // Replace this with your desired end time
        AllSlotsRequestDTO allSlotsRequestDTO = new AllSlotsRequestDTO();
        allSlotsRequestDTO.setStartTime(startDateTime);
        allSlotsRequestDTO.setEndTime(endDateTime);
        when(slotRepository.findByStartTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(new Slot()));

        // Test
        interviewBookingService.getAllSlotsWithDateTime(allSlotsRequestDTO.getStartTime(), allSlotsRequestDTO.getEndTime());

        // Verify
        verify(slotRepository, times(1)).findByStartTimeBetween(startDateTime, endDateTime);
    }

}
