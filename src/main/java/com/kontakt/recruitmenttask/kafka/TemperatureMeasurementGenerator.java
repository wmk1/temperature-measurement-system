package com.kontakt.recruitmenttask.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import com.kontakt.recruitmenttask.repository.TemperatureMeasurementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@DependsOn("cqlSession")
public class TemperatureMeasurementGenerator {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TemperatureMeasurementRepository temperatureMeasurementRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public TemperatureMeasurementGenerator(KafkaTemplate<String, String> kafkaTemplate,
                                           TemperatureMeasurementRepository temperatureMeasurementRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.temperatureMeasurementRepository = temperatureMeasurementRepository;
    }

    @PostConstruct
    public void generateSingleMeasurement() {
        TemperatureMeasurement measurement = new TemperatureMeasurement();
        measurement.setId(UUID.randomUUID());
        measurement.setTemperature(Math.random() * 50);
        measurement.setTimestamp(System.currentTimeMillis());
        measurement.setThermometerId(1);  // Assuming a thermometerId of 1
        measurement.setRoomId(1);  // Assuming a roomId of 1

        // Save measurement to Cassandra
        temperatureMeasurementRepository.save(measurement);

        // Send measurement to Kafka
        kafkaTemplate.send("test", serialize(measurement));
    }

    private String serialize(TemperatureMeasurement measurement) {
        try {
            return objectMapper.writeValueAsString(measurement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void generateMeasurements() {
        for (int i = 0; i < 20000; i++) {
            TemperatureMeasurement measurement = new TemperatureMeasurement();
            measurement.setId(UUID.randomUUID());
            measurement.setTemperature(Math.random() * 50);
            measurement.setTimestamp(System.currentTimeMillis());
            measurement.setThermometerId((int) Math.random() * 6);  // Assuming a thermometerId of 1
            measurement.setRoomId((int) Math.random() * 24);  // Assuming a roomId of 1

            // Save measurement to Cassandra
            temperatureMeasurementRepository.save(measurement);

            // Send measurement to Kafka
            kafkaTemplate.send("test", serialize(measurement));
        }
    }
}
