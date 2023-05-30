package hr.fer.iot.hos.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {

    }

    public Device(String deviceId, String location, Timestamp timestamp, User user) {
        this.deviceId = deviceId;
        this.location = location;
        this.timestamp = timestamp;
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
