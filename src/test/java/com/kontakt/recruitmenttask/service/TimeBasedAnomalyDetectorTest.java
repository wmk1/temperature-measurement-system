package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeBasedAnomalyDetectorTest {
    private TimeBasedAnomalyDetector detector;

    @BeforeEach
    public void setup() {
        detector = new TimeBasedAnomalyDetector();
    }

    @Test
    public void testIsAnomaly() {
        long now = System.currentTimeMillis();

        for (int i = 0; i < 9; i++) {
            assertFalse(detector.isAnomaly(new TemperatureMeasurement(20.0, now - 5000 * i)));
        }
        assertTrue(detector.isAnomaly(new TemperatureMeasurement(30.0, now)));
        assertFalse(detector.isAnomaly(new TemperatureMeasurement(20.0, now + 5000)));
    }
}
