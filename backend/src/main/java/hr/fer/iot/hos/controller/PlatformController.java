package hr.fer.iot.hos.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.iot.hos.model.AppNotification;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/platfrom")
public class PlatformController {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private RecordRepository recordRepository;

    @PostMapping(value = "/alarm/{id}")
    public ResponseEntity<?> alarmUser(@PathVariable Long id) throws FirebaseMessagingException {
        Optional<Record> record= recordRepository.findById(id);
        if(record.isPresent()){
            AppNotification note = new AppNotification("notifikacija", "content", "image");
            firebaseMessagingService.sendNotificationToken(note, "TOKEN");
            return ResponseEntity.badRequest().body(new MessageResponse("New error detected user notified"));
        }
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Internal server error"));
    }
}
