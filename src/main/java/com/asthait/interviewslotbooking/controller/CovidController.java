package com.asthait.interviewslotbooking.controller;

import com.asthait.interviewslotbooking.service.CovidReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/covid")
@RequiredArgsConstructor
public class CovidController {
    private final CovidReportService covidService;
    @GetMapping(value = "/stream-data", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> getCovidDataAsStream(@RequestParam String country) {
        Flux<DataBuffer> rawDataStream = covidService.getRawDataStream(country);
        // Transform the raw data stream into ServerSentEvent stream
        return rawDataStream.map(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer); // Release the buffer
            return ServerSentEvent.builder(new String(bytes)).build();
        });
    }
}