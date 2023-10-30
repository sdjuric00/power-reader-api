package com.powerreaderapi.powerreaderapi.response;

import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;

public record DeviceResponse(Long id, double minOutput, double maxOutput,
                             MeasurementType measurementType, SourceType deviceType) {

    public static DeviceResponse fromDevice(Device device) {

        return new DeviceResponse(
                device.getId(), device.getMinOutput(), device.getMaxOutput(),
                device.getMeasurementType(), device.getDeviceType()
        );
    }

}
