package com.powerreaderapi.powerreaderapi.service;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.PowerReader;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;
import com.powerreaderapi.powerreaderapi.repository.DeviceRepository;
import com.powerreaderapi.powerreaderapi.service.implementation.DeviceService;
import com.powerreaderapi.powerreaderapi.service.interfaces.IPowerReaderService;
import com.powerreaderapi.powerreaderapi.service.kafka.KafkaProducer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.powerreaderapi.powerreaderapi.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeviceServiceTest {

    private DeviceRepository deviceRepository;
    private IPowerReaderService powerReaderService;
    private KafkaProducer kafkaProducer;
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        powerReaderService = mock(IPowerReaderService.class);
        kafkaProducer = mock(KafkaProducer.class);
        deviceService = new DeviceService(deviceRepository, powerReaderService, kafkaProducer);
    }

    @Test
    @DisplayName("TD1 - should throw EntityNotFoundException for getting device by id")
    public void getByIdShouldThrowEntityNotFound() throws EntityNotFoundException {
        when(deviceRepository.findById(DEVICE_NOT_EXIST_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> deviceService.getById(DEVICE_NOT_EXIST_ID));
    }

    @Test
    @DisplayName("TD2 - should return Device for getting device by id")
    public void getByIdShouldReturnDevice() throws EntityNotFoundException {
        Device device = new Device();
        device.setId(DEVICE_EXIST_ID);

        when(deviceRepository.findById(DEVICE_EXIST_ID)).thenReturn(Optional.of(device));
        assertEquals(DEVICE_EXIST_ID, deviceService.getById(DEVICE_EXIST_ID).getId());
    }

    @ParameterizedTest
    @DisplayName("TD3 - should throw OperationCannotBeCompletedException for connecting device")
    @CsvSource(value = {"2.0,1.9", "-1.0,-1.1", "0.0,-0.1"})
    public void connectDeviceToReaderShouldThrowOperationCannotBeCompleted(String minOutParam, String maxOutParam) throws EntityNotFoundException {
        double minOut = Double.parseDouble(minOutParam);
        double maxOut = Double.parseDouble(maxOutParam);

        assertThrows(OperationCannotBeCompletedException.class,
                () -> deviceService.connectDeviceToReader("Name", SourceType.SOLAR_PANEL, MeasurementType.KW, minOut, maxOut));

        verify(deviceRepository, never()).saveAndFlush(any(Device.class));
    }

    @Test
    @DisplayName("TD4 - should throw EntityNotFoundException for connecting device")
    public void connectDeviceToReaderShouldThrowEntityNotFound() throws EntityNotFoundException {
        when(powerReaderService.getDefaultPowerReader()).thenThrow(new EntityNotFoundException("power reader", "1"));

        assertThrows(EntityNotFoundException.class,
                () -> deviceService.connectDeviceToReader("Name", SourceType.SOLAR_PANEL, MeasurementType.KW, 1, 2));

        verify(deviceRepository, never()).saveAndFlush(any(Device.class));
    }

    @Test
    @DisplayName("TD5 - should return true for connecting device")
    public void connectDeviceToReaderShouldSucceed() throws EntityNotFoundException, OperationCannotBeCompletedException {
        when(powerReaderService.getDefaultPowerReader()).thenReturn(new PowerReader());

        boolean result = deviceService.connectDeviceToReader("Name", SourceType.SOLAR_PANEL, MeasurementType.KW, 1, 2);

        assertTrue(result);
        verify(deviceRepository, times(1)).saveAndFlush(any(Device.class));
    }


}
