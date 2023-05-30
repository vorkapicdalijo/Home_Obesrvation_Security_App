package hr.fer.iot.hos.model.payload;

import javax.validation.constraints.NotBlank;

public class DeviceRequest {
    @NotBlank
    private String deviceId;

    @NotBlank
    private String location;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
