package com.powerreaderapi.powerreaderapi.response;

import com.powerreaderapi.powerreaderapi.model.SensorReading;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record SensorReadingResponse(Long id, LocalDateTime timestamp, double value, MeasurementType measurementType,
                                    Long deviceId) {

    public static SensorReadingResponse fromSensorReading(SensorReading sensorReading) {

        return new SensorReadingResponse(sensorReading.getId(), sensorReading.getTimestamp().toLocalDateTime(),
                sensorReading.getValue(), sensorReading.getMeasurementType(), sensorReading.getDeviceId());
    }

}
