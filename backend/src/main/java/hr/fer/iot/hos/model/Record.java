package hr.fer.iot.hos.model;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "image", nullable = false)
    private byte[] image;

    @Transient
    public String imageDisplay;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public Record() {
    }

    public Record(User user, byte[] image, String imageDisplay, Timestamp timestamp, Device device) {
        this.user = user;
        this.image = image;
        this.imageDisplay = imageDisplay;
        this.timestamp = timestamp;
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageDisplay() {
        return imageDisplay;
    }

    public void setImageDisplay(String imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
