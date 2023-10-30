package com.powerreaderapi.powerreaderapi.service.interfaces;

import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.model.PowerReader;

public interface IPowerReaderService {
    PowerReader getDefaultPowerReader() throws EntityNotFoundException;
}
