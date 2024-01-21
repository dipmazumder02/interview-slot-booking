package com.asthait.interviewslotbooking.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static com.asthait.interviewslotbooking.util.AppConfigUtil.API_KEY;
import static com.asthait.interviewslotbooking.util.AppConfigUtil.COVID_API_BASE_URL;


@Service
public class CovidReportService {

    private final WebClient webClient;

    public CovidReportService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(COVID_API_BASE_URL)
                .defaultHeader("x-api-key", API_KEY) // Add API key to the header
                .build();
    }

    public Flux<DataBuffer> getRawDataStream(String country) {
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
