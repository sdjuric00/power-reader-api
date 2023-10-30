package com.powerreaderapi.powerreaderapi.service.implementation;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.exception.WrongReadingException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.SensorReading;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.repository.SensorReadingRepository;
import com.powerreaderapi.powerreaderapi.request.SensorReadingMessage;
import com.powerreaderapi.powerreaderapi.response.SensorReadingResponse;
import com.powerreaderapi.powerreaderapi.service.interfaces.IDeviceService;
import com.powerreaderapi.powerreaderapi.service.interfaces.ISensorReadingService;
import com.powerreaderapi.powerreaderapi.util.Helper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorReadingService implements ISensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    private final IDeviceService deviceService;

    public SensorReadingService(
            SensorReadingRepository sensorReadingRepository,
            IDeviceService deviceService
    ) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.deviceService = deviceService;
    }

    /**
     *
     * @param sensorReadingMessage
     * @return Created Sensor Reading
     * @throws EntityNotFoundException
     * @throws WrongReadingException
     */
    @Override
    public SensorReading createSensorReading(SensorReadingMessage sensorReadingMessage)
            throws EntityNotFoundException, WrongReadingException {
        Device device = deviceService.getById(sensorReadingMessage.deviceId());
        checkReadingData(sensorReadingMessage, device);

        return this.sensorReadingRepository.save(
            new SensorReading(sensorReadingMessage.value(), sensorReadingMessage.measurementType(), sensorReadingMessage.deviceId())
        );
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @return List of all sensor readings that are in wanted time range
     * @throws OperationCannotBeCompletedException
     */
    @Override
    public List<SensorReadingResponse> getReadingsByTime(LocalDateTime startTime, LocalDateTime endTime) throws OperationCannotBeCompletedException {
        if (Helper.checkStartTimeAfterEndTime(startTime, endTime)) {
            throw new OperationCannotBeCompletedException("Start time must be before end time!");
        }

        return this.sensorReadingRepository.getReadingsByTime(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime))
                .stream().map(SensorReadingResponse::fromSensorReading).toList();
    }

    //Check if measurement type and read value are valid for device
    private void checkReadingData(SensorReadingMessage sensorReadingMessage, Device device)
            throws WrongReadingException
    {
        double sensorValue = sensorReadingMessage.value();
        double minOutput = device.getMinOutput();
        double maxOutput = device.getMaxOutput();
        MeasurementType measurementType = sensorReadingMessage.measurementType();

        if (measurementType != device.getMeasurementType()) {
            throw new WrongReadingException(String.format("Reading value for device with id: %d has measurement type mismatch! Value: %f%s",
                    device.getId(), sensorValue, measurementType));
        } else if (sensorValue < minOutput || sensorValue > maxOutput) {
            throw new WrongReadingException(String.format("Reading value for device with id: %d is out of bounds. Value: %f%s",
                    device.getId(), sensorValue, measurementType));
        }
    }
}
