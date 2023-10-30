package com.powerreaderapi.powerreaderapi.service.interfaces;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.exception.WrongReadingException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.SensorReading;
import com.powerreaderapi.powerreaderapi.request.SensorReadingMessage;
import com.powerreaderapi.powerreaderapi.response.SensorReadingResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ISensorReadingService {
    SensorReading createSensorReading(SensorReadingMessage sensorReadingMessage)
            throws EntityNotFoundException, WrongReadingException;

    List<SensorReadingResponse> getReadingsByTime(LocalDateTime startTime, LocalDateTime endTime) throws OperationCannotBeCompletedException;
}
