package com.kontakt.recruitmenttask.repository;

import com.kontakt.recruitmenttask.model.TemperatureMeasurement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemperatureAnomalyRepository extends CassandraRepository<TemperatureMeasurement, UUID> {
    @Query("SELECT thermometerId, COUNT(*) as count FROM anomalies GROUP BY thermometerId HAVING count > :threshold")
    List<Long> findThermometersByAnomalyCountGreaterThan(@Param("threshold") int threshold);

}
