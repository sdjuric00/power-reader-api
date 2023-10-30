package com.powerreaderapi.powerreaderapi.util;

import com.powerreaderapi.powerreaderapi.model.SensorReading;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.request.SensorReadingMessage;

public class TestConstants {

    public static final Long DEVICE_NOT_EXIST_ID = -1L;
    public static final Long DEVICE_EXIST_ID = 1L;

    public static final SensorReadingMessage NO_DEVICE_SENSOR_READING_MESSAGE = new SensorReadingMessage(
            DEVICE_NOT_EXIST_ID, MeasurementType.KW, 2
    );
    public static final SensorReadingMessage SENSOR_READING_MESSAGE_WITH_WATTS = new SensorReadingMessage(
            DEVICE_EXIST_ID, MeasurementType.W, 2
    );
    public static final SensorReading SENSOR_READING = new SensorReading(
            1.1, MeasurementType.W, DEVICE_EXIST_ID
    );

}
