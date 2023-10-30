package com.powerreaderapi.powerreaderapi.service.implementation;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.OperationCannotBeCompletedException;
import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.model.PowerReader;
import com.powerreaderapi.powerreaderapi.model.enums.MeasurementType;
import com.powerreaderapi.powerreaderapi.model.enums.SourceType;
import com.powerreaderapi.powerreaderapi.repository.DeviceRepository;
import com.powerreaderapi.powerreaderapi.response.DeviceResponse;
import com.powerreaderapi.powerreaderapi.service.interfaces.IDeviceService;
import com.powerreaderapi.powerreaderapi.service.interfaces.IPowerReaderService;
import com.powerreaderapi.powerreaderapi.service.kafka.KafkaProducer;
import com.powerreaderapi.powerreaderapi.util.Helper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;

    private final IPowerReaderService powerReaderService;

    private final KafkaProducer kafkaProducer;

    public DeviceService(
            DeviceRepository deviceRepository,
            IPowerReaderService powerReaderService,
            KafkaProducer kafkaProducer
    ) {
        this.deviceRepository = deviceRepository;
        this.powerReaderService = powerReaderService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public List<Device> getAll() {

        return this.deviceRepository.findAll();
    }

    @Override
    public Device getById(Long id) throws EntityNotFoundException {

        return this.deviceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("device", id.toString())
        );
    }

    //Sends all devices that are compatible with power reader to simulation script
    @PostConstruct
    public void sendDevicesToSimulationScript() {
        List<Device> allDevices = getAll();
        List<DeviceResponse> response = allDevices.stream().map(DeviceResponse::fromDevice).toList();

        kafkaProducer.sendDevicesToSimulationScript(response);
    }

    @Override
    public boolean connectDeviceToReader(
         String name,
         SourceType deviceType,
         MeasurementType measurementType,
         double minOutput,
         double maxOutput
    ) throws EntityNotFoundException, OperationCannotBeCompletedException {
        checkOutputValues(minOutput, maxOutput);
        PowerReader powerReader = powerReaderService.getDefaultPowerReader();

        this.deviceRepository.saveAndFlush(new Device(
            name, Helper.generateSerialNumber(), deviceType, measurementType,
            minOutput, maxOutput, powerReader
        ));

        this.sendDevicesToSimulationScript();

        return true;
    }

    private void checkOutputValues(double minOutput, double maxOutput)
            throws OperationCannotBeCompletedException {

        if (maxOutput < minOutput) {
            throw new OperationCannotBeCompletedException("Output values are not valid.");
        }
    }
}
