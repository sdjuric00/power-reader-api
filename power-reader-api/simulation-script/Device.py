
class Device:
    def __init__(self, id, min_output, max_output, measurement_type, device_type):
        self.id = id
        self.min_output = min_output
        self.max_output = max_output
        self.measurement_type = measurement_type
        self.device_type = device_type

    def parse_device_data(device_data):
        return Device(
            id=device_data['id'],
            min_output=device_data['minOutput'],
            max_output=device_data['maxOutput'],
            measurement_type=device_data['measurementType'],
            device_type=device_data['deviceType']
        )