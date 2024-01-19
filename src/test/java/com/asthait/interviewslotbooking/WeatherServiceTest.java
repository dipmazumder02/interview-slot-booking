package com.asthait.interviewslotbooking;

import com.asthait.interviewslotbooking.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void getWeatherInformation_ValidCity_ReturnsWeatherInfo() {
        // Mock
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-api-key", "5EaSudOJEgMowMzWlgHidg==mvf4lc1uC85he1e6");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"temperature\": 25}", HttpStatus.OK));

        // Test
        String weatherInfo = weatherService.getWeatherInformation("london");

        // Verify
        verify(restTemplate, times(1)).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
        assertEquals("{\"temperature\": 25}", weatherInfo);
    }
}
