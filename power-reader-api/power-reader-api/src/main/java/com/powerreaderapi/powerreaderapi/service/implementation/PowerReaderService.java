package com.powerreaderapi.powerreaderapi.service.implementation;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.model.PowerReader;
import com.powerreaderapi.powerreaderapi.repository.PowerReaderRepository;
import com.powerreaderapi.powerreaderapi.service.interfaces.IPowerReaderService;
import org.springframework.stereotype.Service;

import static com.powerreaderapi.powerreaderapi.util.Constants.POWER_READER_ID;

@Service
public class PowerReaderService implements IPowerReaderService {

    private final PowerReaderRepository powerReaderRepository;

    public PowerReaderService(PowerReaderRepository powerReaderRepository) {
        this.powerReaderRepository = powerReaderRepository;
    }

    public PowerReader getDefaultPowerReader() throws EntityNotFoundException {

        return this.powerReaderRepository.findById(POWER_READER_ID).orElseThrow(
                () -> new EntityNotFoundException("power reader", "default")
        );
    }
}
