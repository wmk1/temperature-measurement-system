package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;

public interface AnomalyDetector {
    boolean isAnomaly(TemperatureMeasurement temperatureMeasurement);
}
