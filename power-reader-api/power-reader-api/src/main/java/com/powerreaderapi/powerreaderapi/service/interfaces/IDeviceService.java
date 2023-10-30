package com.powerreaderapi.powerreaderapi.service.interfaces;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;

import java.util.List;

public interface IDeviceService {
    List<Device> getAll();
    Device getById(Long id) throws EntityNotFoundException;
    void sendDevicesToSimulationScript();
    boolean connectDeviceToReader(
            String name,
            SourceType deviceType,
            MeasurementType measurementType,
            double minOutput,
            double maxOutput
    ) throws EntityNotFoundException, OperationCannotBeCompletedException;
}
