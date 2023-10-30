package com.powerreaderapi.powerreaderapi.repository;

import com.powerreaderapi.powerreaderapi.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

//    @Query(value = "SELECT sr FROM sensor_readings sr WHERE time_bucket('1 day', sr.timestamp) = ?1", nativeQuery = true)
//    List<SensorReading> getReadingsForDay(Timestamp targetDay);

    @Query(value = "SELECT sr FROM SensorReading sr WHERE sr.timestamp >= ?1")
    List<SensorReading> getReadingsForDayTest(Timestamp targetDay);

    @Query(value = "SELECT sr FROM SensorReading sr WHERE sr.timestamp >= ?1 AND sr.timestamp <= ?2")
    List<SensorReading> getReadingsByTime(Timestamp startTime, Timestamp endTime);
}
