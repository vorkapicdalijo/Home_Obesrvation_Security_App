package hr.fer.iot.hos.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.iot.hos.model.AppNotification;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.service.FirebaseMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/platfrom")
public class PlatformController {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private RecordRepository recordRepository;

    private final String ATTENTION_LINK = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Attention_Sign.svg/2302px-Attention_Sign.svg.png";

    @PostMapping(value = "/alarm/{id}")
    public ResponseEntity<?> alarmUser(@PathVariable Long id) throws FirebaseMessagingException, IOException {
        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            Record record1 = record.get();
            String token = record1.getUser().getFireBaseToken();
            String content = " http://localhost:4200/records/".concat(id.toString());
            AppNotification note = new AppNotification("ALARM", content, ATTENTION_LINK);
            firebaseMessagingService.sendNotificationToken(note, token);
            return ResponseEntity.badRequest().body(new MessageResponse("New error detected user notified"));
        } else
            return ResponseEntity.badRequest().body(new MessageResponse("Internal server error"));
    }
}
