package com.powerreaderapi.powerreaderapi.request;

import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import lombok.NoArgsConstructor;

public record SensorReadingMessage(Long deviceId, MeasurementType measurementType, double value) {
}
