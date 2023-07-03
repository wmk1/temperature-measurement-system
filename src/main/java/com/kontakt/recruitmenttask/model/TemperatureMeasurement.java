package com.kontakt.recruitmenttask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table(value="measurements")
@NoArgsConstructor
@Getter
@Setter
public class TemperatureMeasurement {

    @PrimaryKey
    private UUID id;

    private double temperature;
    private long timestamp;
    private int thermometerId;
    private int roomId;

    public TemperatureMeasurement(double temperature) {
        this.temperature = temperature;
    }

    public TemperatureMeasurement(double temperature, long timestamp) {
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

}