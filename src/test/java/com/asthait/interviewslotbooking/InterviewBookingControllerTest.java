package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.controller.InterviewBookingController;
import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.dto.response.ResponseDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.util.ResponseMessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterviewBookingControllerTest {

    @Mock
    private InterviewBookingService interviewBookingService;

    @InjectMocks
    private InterviewBookingController interviewBookingController;

    @Test
    public void createBookingSlot_ValidRequest_ReturnsSlot() {
        // Mock
        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        Slot savedSlot = new Slot(); // Mock your slot data
        when(interviewBookingService.createBookingSlot(any(CreateSlotRequestDTO.class))).thenReturn(savedSlot);

        // Test
        ResponseEntity<ResponseDTO<Slot>> responseEntity = interviewBookingController.createBookingSlot(createSlotRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(savedSlot, responseEntity.getBody().getData());
        assertEquals(ResponseMessageUtil.BOOKING_SLOT_CREATED, responseEntity.getBody().getMessage());
    }

    @Test
    public void bookSlot_ValidRequest_ReturnsSuccessMessage() {
        // Mock
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();

        // Test
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.bookSlot(bookingRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ResponseMessageUtil.SLOT_BOOKED, responseEntity.getBody().getMessage());
    }

    @Test
    public void cancelBooking_ValidRequest_ReturnsSuccessMessage() {
        // Mock
        CancelBookingRequestDTO cancelBookingRequestDTO = new CancelBookingRequestDTO();

        // Test
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.cancelBooking(cancelBookingRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ResponseMessageUtil.BOOKING_CANCELED, responseEntity.getBody().getMessage());
    }

    @Test
    public void updateBooking_ValidRequest_ReturnsSuccessMessage() {
        // Mock
        UpdateBookingRequestDTO updateBookingRequestDTO = new UpdateBookingRequestDTO();

        // Test
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.updateBooking(updateBookingRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ResponseMessageUtil.BOOKING_UPDATED, responseEntity.getBody().getMessage());
    }

    @Test
    public void getAllSlotsWithDateTime_ValidRequest_ReturnsSlotList() {
        // Mock
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        AllSlotsRequestDTO allSlotsRequestDTO = new AllSlotsRequestDTO();
        allSlotsRequestDTO.setStartTime(startTime);
        allSlotsRequestDTO.setEndTime(endTime);
        when(interviewBookingService.getAllSlotsWithDateTime(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(new SlotResponseDTO(), new SlotResponseDTO()));

        // Test
        ResponseEntity<ResponseDTO<List<SlotResponseDTO>>> responseEntity = interviewBookingController.getAllSlotsWithDateTime(allSlotsRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getData().size());
        assertEquals(ResponseMessageUtil.OPERATION_SUCCESSFUL, responseEntity.getBody().getMessage());
    }
}
