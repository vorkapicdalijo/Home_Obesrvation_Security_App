package hr.fer.iot.hos.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformRequest {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("user_id")
    private String userId;

    public PlatformRequest(String deviceId, String userId) {
        this.deviceId = deviceId;
        this.userId = userId;
    }
}

