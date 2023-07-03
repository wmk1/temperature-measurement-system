package com.kontakt.recruitmenttask.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import com.kontakt.recruitmenttask.service.TemperatureMeasurementAnalyzer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TemperatureMeasurementConsumer {
    private final TemperatureMeasurementAnalyzer analyzer;

    @Autowired
    public TemperatureMeasurementConsumer(TemperatureMeasurementAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void consume(ConsumerRecord<String, String> record) {
        TemperatureMeasurement measurement = deserialize(record.value());
        analyzer.analyzeMeasurement(measurement);
    }
    private TemperatureMeasurement deserialize(String value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(value, TemperatureMeasurement.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
