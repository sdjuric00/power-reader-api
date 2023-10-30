package com.powerreaderapi.powerreaderapi.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({SensorReadingServiceTest.class, DeviceServiceTest.class})
public class TestSuite {
}
