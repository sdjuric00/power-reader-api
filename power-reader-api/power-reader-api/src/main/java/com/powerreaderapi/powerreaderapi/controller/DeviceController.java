package com.powerreaderapi.powerreaderapi.controller;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.request.NewDeviceRequest;
import com.powerreaderapi.powerreaderapi.service.implementation.DeviceService;
import com.powerreaderapi.powerreaderapi.service.interfaces.IDeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
@Validated
public class DeviceController {

    private final IDeviceService deviceService;

    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('READ_SENSOR_READINGS')")
    public boolean addDeviceToPowerReader(@Valid @RequestBody NewDeviceRequest request)
            throws OperationCannotBeCompletedException, EntityNotFoundException {

        return deviceService.connectDeviceToReader(
                request.getName(),
                request.getDeviceType(),
                request.getMeasurementType(),
                request.getMinOutput(),
                request.getMaxOutput()
        );
    }

}
