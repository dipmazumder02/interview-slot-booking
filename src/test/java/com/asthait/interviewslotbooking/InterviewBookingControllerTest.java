package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.controller.InterviewBookingController;
import com.asthait.interviewslotbooking.dto.request.*;
import com.asthait.interviewslotbooking.dto.response.ResponseDTO;
import com.asthait.interviewslotbooking.dto.response.SlotResponseDTO;
import com.asthait.interviewslotbooking.service.abstraction.InterviewBookingService;
import com.asthait.interviewslotbooking.util.ResponseMessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class InterviewBookingControllerTest {

    @Mock
    private InterviewBookingService interviewBookingService;

    @InjectMocks
    private InterviewBookingController interviewBookingController;

    @Test
    void createBookingSlot_Success() {
        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.createBookingSlot(createSlotRequestDTO);

        assertEquals(ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_SLOT_CREATED)), responseEntity);
        verify(interviewBookingService, times(1)).createBookingSlot(createSlotRequestDTO);
    }

    @Test
    void bookSlot_Success() {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.bookSlot(bookingRequestDTO);

        assertEquals(ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.SLOT_BOOKED)), responseEntity);
        verify(interviewBookingService, times(1)).bookSlot(bookingRequestDTO);
    }

    @Test
    void cancelBooking_Success() {
        CancelBookingRequestDTO cancelBookingRequestDTO = new CancelBookingRequestDTO();
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.cancelBooking(cancelBookingRequestDTO);

        assertEquals(ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_CANCELED)), responseEntity);
        verify(interviewBookingService, times(1)).cancelBooking(cancelBookingRequestDTO);
    }

    @Test
    void updateBooking_Success() {
        UpdateBookingRequestDTO updateBookingRequestDTO = new UpdateBookingRequestDTO();
        ResponseEntity<ResponseDTO<String>> responseEntity = interviewBookingController.updateBooking(updateBookingRequestDTO);

        assertEquals(ResponseEntity.ok(ResponseDTO.success(ResponseMessageUtil.BOOKING_UPDATED)), responseEntity);
        verify(interviewBookingService, times(1)).updateBooking(updateBookingRequestDTO);
    }

    @Test
    void getAllSlotsWithDateTime_Success() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        List<SlotResponseDTO> mockSlotList = Collections.singletonList(new SlotResponseDTO());
        when(interviewBookingService.getAllSlotsWithDateTime(startTime, endTime)).thenReturn(mockSlotList);

        ResponseEntity<ResponseDTO<List<SlotResponseDTO>>> responseEntity = interviewBookingController.getAllSlotsWithDateTime(startTime, endTime);

        assertEquals(ResponseEntity.ok(ResponseDTO.success(mockSlotList, ResponseMessageUtil.OPERATION_SUCCESSFUL)), responseEntity);
        verify(interviewBookingService, times(1)).getAllSlotsWithDateTime(startTime, endTime);
    }
}
