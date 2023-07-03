package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BufferBasedAnomalyDetector implements AnomalyDetector {

    private final List<TemperatureMeasurement> buffer = new ArrayList<>();

    @Override
    public boolean isAnomaly(TemperatureMeasurement measurement) {
        buffer.add(measurement);
        if (buffer.size() == 10) {
            double average = buffer.subList(0, 9).stream()
                    .mapToDouble(TemperatureMeasurement::getTemperature)
                    .average()
                    .orElse(Double.NaN);

            buffer.remove(0);
            return measurement.getTemperature() > average + 5;
        }
        return false;
    }
}
