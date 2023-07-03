package com.kontakt.recruitmenttask.controller;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class AnomalyStreamController {
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(path = "/api/anomalies/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamAnomalies() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void send(TemperatureMeasurement anomaly) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(anomaly);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}