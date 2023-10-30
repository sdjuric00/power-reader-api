package com.powerreaderapi.powerreaderapi.service.kafka;

import com.powerreaderapi.powerreaderapi.model.Device;
import com.powerreaderapi.powerreaderapi.response.DeviceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducer {

    private static final String topic = "devices-information";

    private final KafkaTemplate<String, List<DeviceResponse>> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, List<DeviceResponse>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDevicesToSimulationScript(List<DeviceResponse> devices) {
        kafkaTemplate.send(topic, devices);
        System.out.println("Kafka sent a message with " + devices.size() + " elements.");
    }

}