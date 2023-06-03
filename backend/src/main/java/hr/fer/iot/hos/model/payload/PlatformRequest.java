package hr.fer.iot.hos.model.payload;

import org.springframework.web.multipart.MultipartFile;

public class PlatformRequest {

    String deviceId;

    String timestamp;

    MultipartFile file;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
