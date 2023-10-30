package com.powerreaderapi.powerreaderapi.util;

import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;

public class ErrorMessages {

    //////VALIDATION MESSAGES///////////
    public static final String WRONG_NAME = "Name must contain between 5 and 50 characters";
    public static final String WRONG_MEASUREMENT_TYPE = "Measurement type should exists.";
    public static final String WRONG_DEVICE_TYPE = "Device type should exists.";
    public static final String WRONG_MIN_OUTPUT_VALUE = "Minimal output value must be either 0 or greater.";
    public static final String WRONG_MAX_OUTPUT_VALUE = "Maximal output value must be positive number.";
    public static final String WRONG_EMAIL = "Email must be included in right format. example@domain.com";
    public static final String WRONG_PASSWORD = "Password must be included.";

    //////LOGS///////////

    public static String getWrongFormatReadingMsg() {
        return "Incorrect message format recorded. Message won't be processed.";
    }

    public static String getValueOutOfBoundsReadingMsg(Long deviceId, double value, MeasurementType measurementType) {
        return String.format("Incorrect reading recorded for device with id %d. " +
                "Recorded value %f%s is not valid for device specification.", deviceId, value, measurementType);
    }

    public static String getReadingForWrongDeviceMsg() {
        return "Warning happened. Unknown device detected.";
    }

}
