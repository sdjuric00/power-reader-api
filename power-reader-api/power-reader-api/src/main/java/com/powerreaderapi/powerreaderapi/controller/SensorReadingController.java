package com.powerreaderapi.powerreaderapi.controller;

import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.response.SensorReadingResponse;
import com.powerreaderapi.powerreaderapi.service.interfaces.ISensorReadingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("sensor-reading")
@Validated
public class SensorReadingController {

    private final ISensorReadingService sensorReadingService;

    public SensorReadingController(ISensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @GetMapping("/{startTime}/{endTime}")
    @ResponseStatus(HttpStatus.OK)
    public List<SensorReadingResponse> getReadingsByTime(
            @Valid @PathVariable @NotNull LocalDateTime startTime,
            @Valid @PathVariable @NotNull LocalDateTime endTime
    ) throws OperationCannotBeCompletedException {

        return this.sensorReadingService.getReadingsByTime(startTime, endTime);
    }

}
