package com.powerreaderapi.powerreaderapi.service;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.exception.WrongReadingException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.SensorReading;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.repository.SensorReadingRepository;
import com.powerreaderapi.powerreaderapi.request.SensorReadingMessage;
import com.powerreaderapi.powerreaderapi.service.implementation.SensorReadingService;
import com.powerreaderapi.powerreaderapi.service.interfaces.IDeviceService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.powerreaderapi.powerreaderapi.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SensorReadingServiceTest {

    private SensorReadingRepository sensorReadingRepository;
    private IDeviceService deviceService;
    private SensorReadingService sensorReadingService;

    @BeforeEach
    void setUp() {
        sensorReadingRepository = mock(SensorReadingRepository.class);
        deviceService = mock(IDeviceService.class);
        sensorReadingService = new SensorReadingService(sensorReadingRepository, deviceService);
    }

    @Test
    @DisplayName("TS1 - Should throw EntityNotFoundException on sensor reading creation")
    public void sensorReadingCreationThrowsEntityNotFound() throws EntityNotFoundException {
        when(deviceService.getById(DEVICE_NOT_EXIST_ID)).thenThrow(new EntityNotFoundException("device", DEVICE_NOT_EXIST_ID.toString()));
        assertThrows(EntityNotFoundException.class,
                () -> sensorReadingService.createSensorReading(NO_DEVICE_SENSOR_READING_MESSAGE));
    }

    @Test
    @DisplayName("TS2 - Should throw WrongReadingException on sensor reading creation, wrong measurement type")
    public void sensorReadingCreationThrowsWrongReadingOnMeasurementTypeMismatch() throws EntityNotFoundException {
        Device device = new Device();
        device.setMeasurementType(MeasurementType.KW);  //device with KW

        when(deviceService.getById(DEVICE_EXIST_ID)).thenReturn(device);
        WrongReadingException exception = assertThrows(WrongReadingException.class,
                () -> sensorReadingService.createSensorReading(SENSOR_READING_MESSAGE_WITH_WATTS));

        assertTrue(exception.getMessage().contains("measurement type mismatch"));
        verify(sensorReadingRepository, never()).save(any(SensorReading.class));
    }

    @ParameterizedTest
    @DisplayName("TS3 - Should throw WrongReadingException on sensor reading creation, wrong value")
    @ValueSource(doubles = {-0.1, 1.1})
    public void sensorReadingCreationThrowsWrongReadingOnValueOutOfBounds(double value) throws EntityNotFoundException {
        Device device = new Device();
        device.setMeasurementType(MeasurementType.W);
        device.setMinOutput(0.0);
        device.setMaxOutput(1); //Device with certain rage and W type

        //SensorReading with wrong value, out of range for created device
        SensorReadingMessage sensorReadingMessage = new SensorReadingMessage(DEVICE_EXIST_ID, MeasurementType.W, value);

        when(deviceService.getById(DEVICE_EXIST_ID)).thenReturn(device);
        WrongReadingException exception = assertThrows(WrongReadingException.class,
                () -> sensorReadingService.createSensorReading(sensorReadingMessage));

        assertTrue(exception.getMessage().contains("out of bounds"));
        verify(sensorReadingRepository, never()).save(any(SensorReading.class));
    }

    @Test
    @DisplayName("TS4 - Should successfully create sensor reading")
    public void sensorReadingCreationSuccessful() throws EntityNotFoundException, WrongReadingException {
        double value = 0.5;
        Device device = new Device();
        device.setMeasurementType(MeasurementType.W);
        device.setMinOutput(0.0);
        device.setMaxOutput(1); //Device with certain rage and W type
        SensorReadingMessage sensorReadingMessage = new SensorReadingMessage(DEVICE_EXIST_ID, MeasurementType.W, value);

        when(deviceService.getById(DEVICE_EXIST_ID)).thenReturn(device);
        when(sensorReadingRepository.save(any(SensorReading.class))).thenReturn(
                new SensorReading(value, MeasurementType.W, DEVICE_EXIST_ID)
        );

        assertEquals(DEVICE_EXIST_ID, sensorReadingService.createSensorReading(sensorReadingMessage).getDeviceId());
        verify(sensorReadingRepository, times(1)).save(any(SensorReading.class));
    }

    @Test
    @DisplayName("TS5 - Should throw OperationCannotBeCompletedException when reading sensor readings")
    public void readingSensorReadingsThrowsOperationCannotBeCompleted() {

        assertThrows(OperationCannotBeCompletedException.class,
                () -> sensorReadingService.getReadingsByTime(LocalDateTime.now(), LocalDateTime.now().minusSeconds(1)));
    }

    @Test
    @DisplayName("TS6 - Should successfully return sensor readings")
    public void readingSensorReadingsSuccessful() throws OperationCannotBeCompletedException {
        LocalDateTime currentTime = LocalDateTime.now();
        List<SensorReading> readings = new ArrayList<>();
        readings.add(SENSOR_READING);
        readings.add(SENSOR_READING);

        when(sensorReadingRepository.getReadingsByTime(any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(readings);

        assertEquals(2, sensorReadingService.getReadingsByTime(currentTime, currentTime).size());
        verify(sensorReadingRepository, times(1))
                .getReadingsByTime(any(Timestamp.class), any(Timestamp.class));
    }

}
