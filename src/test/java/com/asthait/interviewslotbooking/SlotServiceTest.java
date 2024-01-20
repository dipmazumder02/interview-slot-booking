package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.dto.request.CreateSlotRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.service.SlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotService slotService;

    @Test
    void getSlotBySlotId_ValidSlotId_ReturnsSlot() {
        Long slotId = 1L;
        Slot expectedSlot = new Slot();
        expectedSlot.setId(slotId);

        when(slotRepository.findById(slotId)).thenReturn(Optional.of(expectedSlot));

        Slot result = slotService.getSlotBySlotId(slotId);

        assertEquals(expectedSlot, result);
    }

    @Test
    void getSlotBySlotId_InvalidSlotId_ThrowsBookingException() {
        Long invalidSlotId = 99L;

        when(slotRepository.findById(invalidSlotId)).thenReturn(Optional.empty());

        assertThrows(BookingException.class, () -> slotService.getSlotBySlotId(invalidSlotId));
    }

    @Test
    void createSlot_ValidCreateSlotRequest_CreatesAndReturnsSlot() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        createSlotRequestDTO.setStartTime(startTime);
        createSlotRequestDTO.setEndTime(endTime);

        when(slotRepository.findByStartTimeBetween(startTime, endTime)).thenReturn(Arrays.asList());

        Slot createdSlot = new Slot();
        createdSlot.setId(1L);
        createdSlot.setStartTime(startTime);
        createdSlot.setEndTime(endTime);
        createdSlot.setStatus(BookingSlotStatus.AVAILABLE);

        when(slotRepository.save(any(Slot.class))).thenReturn(createdSlot);

        Slot result = slotService.createSlot(createSlotRequestDTO);

        assertNotNull(result);
        assertEquals(createdSlot.getId(), result.getId());
        assertEquals(createdSlot.getStartTime(), result.getStartTime());
        assertEquals(createdSlot.getEndTime(), result.getEndTime());
        assertEquals(createdSlot.getStatus(), result.getStatus());
    }

    @Test
    void createSlot_InvalidTimeRange_ThrowsBookingException() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(2);

        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        createSlotRequestDTO.setStartTime(startTime);
        createSlotRequestDTO.setEndTime(endTime);

        assertThrows(BookingException.class, () -> slotService.createSlot(createSlotRequestDTO));
    }

    @Test
    void createSlot_OverlappingSlots_ThrowsBookingException() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        CreateSlotRequestDTO createSlotRequestDTO = new CreateSlotRequestDTO();
        createSlotRequestDTO.setStartTime(startTime);
        createSlotRequestDTO.setEndTime(endTime);

        Slot overlappingSlot = new Slot();
        overlappingSlot.setId(1L);

        when(slotRepository.findByStartTimeBetween(startTime, endTime)).thenReturn(Arrays.asList(overlappingSlot));

        assertThrows(BookingException.class, () -> slotService.createSlot(createSlotRequestDTO));
    }

}
