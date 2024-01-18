package com.asthait.interviewslotbooking.dto.response;
import lombok.Data;

/**
 * Generic response DTO to standardize the JSON response format.
 */
@Data
public class ResponseDTO<T> {

    private boolean success;
    private T data;
    private String message;

    public ResponseDTO(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseDTO<T> success(String message) {
        return new ResponseDTO<>(true, null, message);
    }
    public static <T> ResponseDTO<T> success(T data, String message) {
        return new ResponseDTO<>(true, data, message);
    }

    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>(false, null, message);
    }
}
