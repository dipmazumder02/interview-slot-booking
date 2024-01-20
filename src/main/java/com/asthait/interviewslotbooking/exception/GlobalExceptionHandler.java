package com.asthait.interviewslotbooking.exception;

import com.asthait.interviewslotbooking.dto.response.ErrorResponseDTO;
import com.asthait.interviewslotbooking.dto.response.ResponseDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingException(BookingException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO<String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ResponseDTO.error("Validation error: " + ex.getFieldError().getDefaultMessage()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.badRequest().body(ResponseDTO.error(errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ResponseDTO.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO<String>> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.badRequest().body(ResponseDTO.error(ex.getMessage()));
    }
}
