package com.kontakt.recruitmenttask.config;

import com.kontakt.recruitmenttask.repository.TemperatureAnomalyRepository;
import com.kontakt.recruitmenttask.service.AnomalyDetector;
import com.kontakt.recruitmenttask.service.BufferBasedAnomalyDetector;
import com.kontakt.recruitmenttask.service.TimeBasedAnomalyDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnomalyDetectorConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectorConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "anomaly.detector.type", havingValue = "bufferBased")
    public AnomalyDetector bufferedAnomalyDetector() {
        logger.info("Creating BufferBasedAnomalyDetector bean");
        return new BufferBasedAnomalyDetector();
    }

    @Bean
    @ConditionalOnProperty(name = "anomaly.detector.type", havingValue = "timeBased")
    public AnomalyDetector timeBasedAnomalyDetector() {
        logger.info("Creating BufferBasedAnomalyDetector bean");
        return new TimeBasedAnomalyDetector();
    }
}
