package hr.fer.iot.hos.controller;

import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.model.payload.Record;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.repository.UserRespository;
import hr.fer.iot.hos.service.FaceDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class FaceController {

    @Autowired
    FaceDetectionService faceDetectionService;

    @Autowired
    UserRespository userRespository;
    @Autowired
    RecordRepository recordRepository;

    @GetMapping(value = "/records")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Record>> getRecords(Authentication auth){
        User userDb = userRespository.findByUsername(auth.getName()).get();
        Collection<Record> records = recordRepository.findByUser(userDb);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/image")
    public ResponseEntity<?> postImage(@RequestParam("file") MultipartFile file, Authentication auth) {

        byte[] bytes = null;
        try {
            bytes = faceDetectionService.detectFaceApi(file.getBytes()).toImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Record record = new Record();
        if(userRespository.existsByUsername(auth.getName())){
            User userDb = userRespository.findByUsername(auth.getName()).get();
            record.setUser(userDb);
            record.setImage(bytes);
            record.setImageDisplay(Base64.getEncoder().encodeToString(bytes));
            record.setTimestamp(new Timestamp(System.currentTimeMillis()));
            recordRepository.save(record);
            return ResponseEntity.ok(new MessageResponse("Face detection finished!"));
        }

        return ResponseEntity.ok(new MessageResponse("Error occured!"));

    }
}
