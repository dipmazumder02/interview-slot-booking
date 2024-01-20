package com.asthait.interviewslotbooking.service;

import com.asthait.interviewslotbooking.exception.BookingException;
import com.asthait.interviewslotbooking.util.ExceptionMessageUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/*https://api-ninjas.com/api/weather*/
@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    private final static String WEATHER_API_BASE_URL = "https://api.api-ninjas.com/v1/weather";
    private final static String WEATHER_API_KEY = "5EaSudOJEgMowMzWlgHidg==mvf4lc1uC85he1e6";

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherInformation(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-api-key", WEATHER_API_KEY);

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
