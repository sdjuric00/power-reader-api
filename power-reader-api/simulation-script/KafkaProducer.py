import json

from kafka import KafkaProducer

producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

def send_message(device, random_output):
    sensor_reading = {"deviceId": device.id, "measurementType": device.measurement_type, "value": random_output}
    producer.send('sensor-reading', value=sensor_reading)
    print(f"Message sent.\n")