package com.kontakt.recruitmenttask.service;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BufferBasedAnomalyDetectorTest {
    private BufferBasedAnomalyDetector detector;

    @BeforeEach
    public void setup() {
        detector = new BufferBasedAnomalyDetector();
    }

    @Test
    public void testIsAnomaly() {
        for (int i = 0; i < 9; i++) {
            assertFalse(detector.isAnomaly(new TemperatureMeasurement(20.0)));
        }
        assertTrue(detector.isAnomaly(new TemperatureMeasurement(30.0)));
        assertFalse(detector.isAnomaly(new TemperatureMeasurement(20.0)));
    }
}