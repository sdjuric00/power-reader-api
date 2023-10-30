package com.powerreaderapi.powerreaderapi.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerreaderapi.powerreaderapi.exception.EntityNotFoundException;
import com.powerreaderapi.powerreaderapi.exception.WrongReadingException;
import com.powerreaderapi.powerreaderapi.request.SensorReadingMessage;
import com.powerreaderapi.powerreaderapi.service.interfaces.ISensorReadingService;
import com.powerreaderapi.powerreaderapi.util.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class KafkaConsumer {

    private final ISensorReadingService sensorReadingService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaConsumer(ISensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @KafkaListener(topics = "sensor-reading", groupId = "power-reader")
    public void consume(HashMap<String, String> sensorReading)
    {
        System.out.println("Received updated reading: " + sensorReading);
        processData(sensorReading);
    }

    //Checks if read message is in valid format, create logs if not
    private void processData(HashMap<String, String> sensorReading) {
        SensorReadingMessage sensorReadingMessage = null;
        try {
            sensorReadingMessage = convertSensorReading(sensorReading);
            this.sensorReadingService.createSensorReading(sensorReadingMessage);
        } catch (IllegalArgumentException e) {
            handleIllegalArgumentException();
        } catch (WrongReadingException e) {
            handleWrongReadingException(sensorReadingMessage);
        } catch (EntityNotFoundException e) {
            handleEntityNotFoundException();
        }
    }

    private SensorReadingMessage convertSensorReading(HashMap<String, String> sensorReading) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(sensorReading, SensorReadingMessage.class);
    }

    private void handleIllegalArgumentException() {
        String message = ErrorMessages.getWrongFormatReadingMsg();
        logger.warn(message);
    }

    private void handleWrongReadingException(SensorReadingMessage sensorReadingMessage) {
        String message =  ErrorMessages.getValueOutOfBoundsReadingMsg(
            sensorReadingMessage.deviceId(), sensorReadingMessage.value(), sensorReadingMessage.measurementType()
        );
        logger.error(message);
    }

    private void handleEntityNotFoundException() {
        String message = ErrorMessages.getReadingForWrongDeviceMsg();
        logger.warn(message);
    }

}