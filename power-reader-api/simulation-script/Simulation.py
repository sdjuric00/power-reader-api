import threading

from KafkaConsumer import kafka_consumer_thread, all_devices
from KafkaProducer import send_message

import random
import time
from datetime import datetime

now = datetime.now()

errorDifference = 0.1


def is_possible_daylight():
    return 7 <= now.hour <= 19


# Filter devices based on time, returns solar panels for day and batteries for night
def filter_devices():
    if is_possible_daylight():
        return [device for device in all_devices if device.device_type == "SOLAR_PANEL"]
    else:
        return [device for device in all_devices if device.device_type == "BATTERY_STORAGE"]


# Picks random device and returns random value
def run_simulation():
    filtered_devices = filter_devices()

    if (len(filtered_devices) > 0):
        selected_device = random.choice(filtered_devices)
        random_output = random.uniform(selected_device.min_output, selected_device.max_output + errorDifference)

        print(
            f"Selected Device with id: {selected_device.id}\nOutput: {random_output}{selected_device.measurement_type}")
        send_message(selected_device, random_output)


def start_simulation():
    while True:
        if (len(all_devices) > 0):
            run_simulation()
            time.sleep(5)  # sleep 5 sec


if __name__ == '__main__':
    kafka_thread = threading.Thread(target=kafka_consumer_thread)
    kafka_thread.daemon = True
    kafka_thread.start()
    start_simulation()
