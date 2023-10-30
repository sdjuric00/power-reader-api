package com.powerreaderapi.powerreaderapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    CONNECT_DEVICE("connect_device"),
    READ_SENSOR_READINGS("read_sensor_readings");

    private final String permission;
}
