package com.kontakt.recruitmenttask.repository;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemperatureMeasurementRepository extends CassandraRepository<TemperatureMeasurement, UUID> {
    @Query("SELECT * FROM measurements WHERE thermometerId = :thermometerId AND temperature > 80 ALLOW FILTERING")
    List<TemperatureMeasurement> findAnomaliesByThermometerId(@Param("thermometerId") long thermometerId);

    @Query("SELECT * FROM measurements WHERE roomId = :roomId AND temperature > 80 ALLOW FILTERING")
    List<TemperatureMeasurement> findAnomaliesByRoomId(@Param("roomId") long roomId);

}
