package com.asthait.interviewslotbooking.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class CovidReportService {

    private final WebClient webClient;
    private final static String COVID_API_BASE_URL = "https://api.api-ninjas.com/v1/covid19";
    private final static String API_KEY = "5EaSudOJEgMowMzWlgHidg==mvf4lc1uC85he1e6";
    public CovidReportService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(COVID_API_BASE_URL)
                .defaultHeader("x-api-key", API_KEY) // Add API key to the header
                .build();
    }

    public Flux<DataBuffer> getRawDataStream(String country) {
        // Your logic to fetch raw data and convert it to a Flux<String>
        // For example, using WebClient to call an external API and retrieve raw JSON
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("country", country)
                        .build())
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .delaySubscription(Duration.ofSeconds(5))
                .repeat();
    }
}
