package com.kontakt.recruitmenttask.controller;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import com.kontakt.recruitmenttask.repository.TemperatureAnomalyRepository;
import com.kontakt.recruitmenttask.repository.TemperatureMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TemperatureMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemperatureMeasurementRepository temperatureMeasurementRepository;

    @MockBean
    private TemperatureAnomalyRepository temperatureAnomalyRepository;

    @Test
    public void shouldListAllAnomaliesPerThermometherId() throws Exception {
        // Given
        long thermometerId = 1L;
        List<TemperatureMeasurement> anomalies = Arrays.asList(new TemperatureMeasurement(), new TemperatureMeasurement());
        when(temperatureMeasurementRepository.findAnomaliesByThermometerId(thermometerId)).thenReturn(anomalies);

        // When & Then
        this.mockMvc.perform(get("/api/thermometers/{thermometerId}/anomalies", thermometerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldListAllAnomaliesPerRoomId() throws Exception {
        // Given
        long roomId = 1L;
        List<TemperatureMeasurement> anomalies = Arrays.asList(new TemperatureMeasurement(), new TemperatureMeasurement()); // replace with actual anomalies
        when(temperatureMeasurementRepository.findAnomaliesByThermometerId(roomId)).thenReturn(anomalies);

        // When & Then
        this.mockMvc.perform(get("/api/rooms/{roomId}/anomalies", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Assuming 2 anomalies are returned
    }

    @Test
    public void shouldListThermometersByAnomalyCount() throws Exception {
        // Given
        int threshold = 5;
        List<Long> thermometerIds = Arrays.asList(1L, 2L); // replace with actual thermometer IDs
        when(temperatureAnomalyRepository.findThermometersByAnomalyCountGreaterThan(threshold)).thenReturn(thermometerIds);

        // When & Then
        this.mockMvc.perform(get("/api/thermometers/anomalies/count").param("threshold", String.valueOf(threshold)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Assuming 2 thermometer IDs are returned
                .andExpect(jsonPath("$[0]", is(1))) // Check if first ID is 1
                .andExpect(jsonPath("$[1]", is(2))); // Check if second ID is 2
    }
}
