package hr.fer.iot.hos.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.iot.hos.model.AppNotification;
import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.model.payload.PlatformRequest;
import hr.fer.iot.hos.repository.DeviceRepository;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.repository.UserRespository;
import hr.fer.iot.hos.service.FirebaseMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;

@RestController
@RequestMapping("/api/platform")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlatformController {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private DeviceRepository deviceRepository;

    private void sendAlarm(String firebaseToken, String id) {
        String content = " http://localhost:4200/records/".concat(id);
        String ATTENTION_LINK = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Attention_Sign.svg/2302px-Attention_Sign.svg.png";
        AppNotification note = new AppNotification("ALARM", content, ATTENTION_LINK);
        try {
            firebaseMessagingService.sendNotificationToken(note, firebaseToken);
        } catch (FirebaseMessagingException e) {
            logger.error("Error while sending firebase alarm");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/alarm")
    public ResponseEntity<?> postImage(@RequestBody PlatformRequest platformRequest) {

        byte[] bytes = null;
        try {
            bytes = platformRequest.getFile().getBytes();
        } catch (IOException e) {
            logger.error("while processing image", e);
        }

        Record record = new Record();
        Device device = deviceRepository.findByDeviceId(platformRequest.getDeviceId());
        if (device != null) {
            User userDb = device.getUser();
            record.setUser(userDb);
            record.setImage(bytes);
            record.setImageDisplay(Base64.getEncoder().encodeToString(bytes));
            record.setTimestamp(Timestamp.valueOf(platformRequest.getTimestamp()));
            record.setDevice(device);
            recordRepository.save(record);
            sendAlarm(userDb.getFireBaseToken(), String.valueOf(userDb.getId()));
            return ResponseEntity.ok(new MessageResponse("Image uploaded!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: while uploading image"));
    }
}
