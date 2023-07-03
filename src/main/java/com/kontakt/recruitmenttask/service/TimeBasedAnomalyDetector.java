package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TimeBasedAnomalyDetector implements AnomalyDetector {

    private final Queue<TemperatureMeasurement> window = new LinkedList<>();
    private double temperatureSum = 0.0;

    @Override
    public boolean isAnomaly(TemperatureMeasurement measurement) {

        window.add(measurement);
        temperatureSum += measurement.getTemperature();

        long windowSizeInSeconds = 10L;
        while (!window.isEmpty() && measurement.getTimestamp() - window.peek().getTimestamp() > windowSizeInSeconds * 1000) {
            temperatureSum -= window.peek().getTemperature();
            window.poll();
        }

        double average = temperatureSum / window.size();
        return measurement.getTemperature() > average + 5;
    }
}

