import json
from kafka import KafkaConsumer
from Device import Device

all_devices = []

consumer = KafkaConsumer(
    'devices-information',
    bootstrap_servers=['localhost:9092'],
    group_id='power-reader',
    value_deserializer=lambda m: json.loads(m.decode('utf-8'))
)

# Reading all devices
def kafka_consumer_thread():
    global allDevices

    for msg in consumer:
        all_devices.clear()
        for device_data in msg.value:
            device = Device.parse_device_data(device_data)
            all_devices.append(device)
            print(f"Device received[id]: {device.id}")


