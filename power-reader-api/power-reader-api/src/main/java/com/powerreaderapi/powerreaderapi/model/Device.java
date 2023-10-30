package com.powerreaderapi.powerreaderapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private SourceType deviceType;

    @Column(nullable = false)
    private MeasurementType measurementType;

    @Column(nullable = false)
    private double minOutput = 0.0;

    @Column(nullable = false)
    private double maxOutput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_reader_id", nullable = false)
    @JsonIgnore
    private PowerReader powerReader;

    public Device(String name,
                  String serialNumber,
                  SourceType deviceType,
                  MeasurementType measurementType,
                  double minOutput,
                  double maxOutput,
                  PowerReader powerReader
    ) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.measurementType = measurementType;
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
        this.powerReader = powerReader;
    }
}
