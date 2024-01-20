package com.asthait.interviewslotbooking.deserializer;

import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateTimeString = p.getValueAsString();
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            throw new BookingException(ExceptionMessageUtil.INVALID_DATE_FORMAT);
        }
    }
}

