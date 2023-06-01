export class Device {
  deviceId: string;
  location: string;
  timestamp: string;

  constructor(deviceId: string, location: string, timestamp: string) {
    this.deviceId = deviceId;
    this.location = location;
    this.timestamp = timestamp;
  }
}
