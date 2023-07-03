package com.kontakt.recruitmenttask.controller;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import com.kontakt.recruitmenttask.repository.TemperatureAnomalyRepository;
import com.kontakt.recruitmenttask.repository.TemperatureMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class TemperatureMeasurementController {

    private final TemperatureMeasurementRepository temperatureMeasurementRepository;

    private final TemperatureAnomalyRepository temperatureAnomalyRepository;

    @Autowired
    public TemperatureMeasurementController(TemperatureMeasurementRepository temperatureMeasurementRepository,
                                            TemperatureAnomalyRepository temperatureAnomalyRepository) {
        this.temperatureMeasurementRepository = temperatureMeasurementRepository;
        this.temperatureAnomalyRepository = temperatureAnomalyRepository;
    }

    @GetMapping("/thermometers/{thermometerId}/anomalies")
    public ResponseEntity<List<TemperatureMeasurement>> listAllAnomaliesByThermometerId(
            @PathVariable long thermometerId
    ) {
        var anomaliesList = temperatureMeasurementRepository.findAnomaliesByThermometerId(thermometerId);
        return ResponseEntity.ok(anomaliesList);
    }

    @GetMapping("/rooms/{roomId}/anomalies")
    public ResponseEntity<List<TemperatureMeasurement>> listAllAnomaliesByRoomId(
            @PathVariable long roomId
    ) {
        var anomaliesList = temperatureMeasurementRepository.findAnomaliesByThermometerId(roomId);
        return ResponseEntity.ok(anomaliesList);
    }

    @GetMapping("/thermometers/anomalies/count")
    public ResponseEntity<List<Long>> listThermometersByAnomalyCount(@RequestParam int threshold) {
        var thermometerIds = temperatureAnomalyRepository.findThermometersByAnomalyCountGreaterThan(threshold);
        return ResponseEntity.ok(thermometerIds);
    }
}

