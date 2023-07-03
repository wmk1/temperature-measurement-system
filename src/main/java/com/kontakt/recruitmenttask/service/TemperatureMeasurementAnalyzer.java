package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.controller.AnomalyStreamController;
import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import com.kontakt.recruitmenttask.repository.TemperatureAnomalyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TemperatureMeasurementAnalyzer {

    private final BufferBasedAnomalyDetector bufferBasedAnomalyDetector;
    private final TimeBasedAnomalyDetector timeBasedAnomalyDetector;
    private final TemperatureAnomalyRepository temperatureAnomalyRepository;
    private final AnomalyStreamController anomalyStreamController;
    private final Environment env;

    @Autowired
    public TemperatureMeasurementAnalyzer(BufferBasedAnomalyDetector bufferBasedAnomalyDetector,
                                          TimeBasedAnomalyDetector timeBasedAnomalyDetector,
                                          TemperatureAnomalyRepository temperatureAnomalyRepository,
                                          AnomalyStreamController anomalyStreamController,
                                          Environment env) {
        this.bufferBasedAnomalyDetector = bufferBasedAnomalyDetector;
        this.timeBasedAnomalyDetector = timeBasedAnomalyDetector;
        this.temperatureAnomalyRepository = temperatureAnomalyRepository;
        this.anomalyStreamController = anomalyStreamController;
        this.env = env;
    }

    public void analyzeMeasurement(TemperatureMeasurement measurement) {
        AnomalyDetector anomalyDetector;
        String detectorType = env.getProperty("anomaly.detector.type");
        if ("bufferBased".equals(detectorType)) {
            anomalyDetector = bufferBasedAnomalyDetector;
        } else if ("timeBased".equals(detectorType)) {
            anomalyDetector = timeBasedAnomalyDetector;
        } else {
            throw new IllegalArgumentException("Invalid anomaly detector type: " + detectorType);
        }

        if (anomalyDetector.isAnomaly(measurement)) {
            temperatureAnomalyRepository.save(measurement);
            anomalyStreamController.send(measurement);
        }
    }
}
