package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.dto.request.CreateSlotRequestDTO;
import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.model.BookingSlotStatus;
import com.asthait.interviewslotbooking.model.Slot;
import com.asthait.interviewslotbooking.repository.SlotRepository;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;

    public Slot getSlotBySlotId(Long slotId) {
        return slotRepository.findById(slotId)
                .orElseThrow(() -> new BookingException(ExceptionMessageUtil.INVALID_SLOT_ID));
    }

    @Transactional
    public Slot createSlot(CreateSlotRequestDTO createSlotRequestDTO) {
        LocalDateTime startTime = createSlotRequestDTO.getStartTime();
        LocalDateTime endTime = createSlotRequestDTO.getEndTime();
        validateCreateSlotRequest(startTime, endTime);
        validateSlotOverlap(startTime, endTime);

        Slot slot = new Slot();
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setStatus(BookingSlotStatus.AVAILABLE);
        log.info("Created slot: {}", slot);
        return slotRepository.save(slot);
    }

    private void validateSlotOverlap(LocalDateTime startTime, LocalDateTime endTime) {
        List<Slot> overlappingSlots = slotRepository.findByStartTimeBetween(startTime, endTime);
        if (!overlappingSlots.isEmpty()) {
            log.error(ExceptionMessageUtil.SLOT_OVERLAP);
            throw new BookingException(ExceptionMessageUtil.SLOT_OVERLAP);
        }
    }

    private void validateCreateSlotRequest(LocalDateTime startTime, LocalDateTime endTime) {
        // Check if start time is before end time
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            log.error(ExceptionMessageUtil.START_TIME_AFTER_END_TIME);
            throw new BookingException(ExceptionMessageUtil.START_TIME_AFTER_END_TIME);
        }
    }
}