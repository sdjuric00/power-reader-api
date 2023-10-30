package com.powerreaderapi.powerreaderapi.request;

import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.powerreaderapi.powerreaderapi.util.Constants.NAME_REGEX;
import static com.powerreaderapi.powerreaderapi.util.ErrorMessages.*;

@Getter
@Setter
@AllArgsConstructor
public class NewDeviceRequest {

    @NotBlank(message = WRONG_NAME)
    @Pattern(message = WRONG_NAME, regexp = NAME_REGEX)
    private final String name;

    @NotNull(message = WRONG_DEVICE_TYPE)
    private SourceType deviceType;

    @NotNull(message = WRONG_MEASUREMENT_TYPE)
    private MeasurementType measurementType;

    @PositiveOrZero(message = WRONG_MIN_OUTPUT_VALUE)
    private double minOutput;

    @Positive(message = WRONG_MAX_OUTPUT_VALUE)
    private double maxOutput;

}
