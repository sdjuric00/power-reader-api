package com.powerreaderapi.powerreaderapi.model;

import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "sensor_readings")
@Getter
@Setter
@NoArgsConstructor
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp timestamp;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private MeasurementType measurementType;

    @Column
    private Long deviceId;

    public SensorReading(
         double value,
         MeasurementType measurementType,
         Long deviceId
    ) {
        this.value = value;
        this.measurementType = measurementType;
        this.deviceId = deviceId;
    }

    public SensorReading(
            double value,
            MeasurementType measurementType,
            Long deviceId,
            Timestamp timestamp
    ) {
        this.value = value;
        this.measurementType = measurementType;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }


}
