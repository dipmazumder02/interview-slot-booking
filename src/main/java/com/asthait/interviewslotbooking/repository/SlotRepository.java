package com.asthait.interviewslotbooking.repository;
import com.asthait.interviewslotbooking.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
