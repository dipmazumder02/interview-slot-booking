package com.asthait.interviewslotbooking.util;

public class ExceptionMessageUtil {
        public static final String INVALID_INTERVIEWER_ID = "Invalid interviewer ID";
        public static final String INVALID_SLOT_ID = "Invalid slot ID";
        public static final String SLOT_ALREADY_BOOKED = "The specified slot is already booked";
        public static final String SLOT_OVERLAP = "Slot overlaps with existing slot";
        public static final String START_TIME_AFTER_END_TIME = "Start time must be before the end time";
        public static final String INVALID_INTERVIEW_BOOKING_SLOT_ID = "Invalid interview booking slot ID";
        public static final String BOOKING_NOT_FOUND = "Booking not found for the provided ID";
        public static final String SLOT_ALREADY_BOOKED_BY_INTERVIEWER = "This slot is already booked by the specified interviewer";
        public static final String FAILED_TO_BOOK_SLOT = "Failed to book slot after multiple attempts. Please try again.";
        public static final String INVALID_DATE_FORMAT = "Invalid LocalDateTime format.";
    }
