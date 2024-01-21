package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.util.AppConfigUtil;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.asthait.interviewslotbooking.util.AppConfigUtil.API_KEY;
import static com.asthait.interviewslotbooking.util.AppConfigUtil.WEATHER_API_BASE_URL;


/*https://api-ninjas.com/api/weather*/
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final RestTemplate restTemplate;
    public String getWeatherInformation(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-api-key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = WEATHER_API_BASE_URL + "?city=" + city;
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new BookingException(ExceptionMessageUtil.WEATHER_API_FAIL);
        }
    }

}
