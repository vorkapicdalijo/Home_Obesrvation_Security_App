package hr.fer.iot.hos.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.iot.hos.model.AppNotification;
import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.DeviceRequest;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.repository.DeviceRepository;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.repository.UserRespository;
import hr.fer.iot.hos.service.FaceDetectionService;
import hr.fer.iot.hos.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    FaceDetectionService faceDetectionService;

    @Autowired
    UserRespository userRespository;
    @Autowired
    RecordRepository recordRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @GetMapping(value = "/records")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Record>> getRecords(Authentication auth){
        User userDb = userRespository.findByUsername(auth.getName()).get();
        Collection<Record> records = recordRepository.findByUser(userDb);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/image")
    public ResponseEntity<?> postImage(@RequestParam("file") MultipartFile file, @RequestParam("device") String deviceId, Authentication auth) {

        byte[] bytes = null;
        try {
            bytes = faceDetectionService.detectFaceApi(file.getBytes()).toImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Record record = new Record();
        if(userRespository.existsByUsername(auth.getName())){
            User userDb = userRespository.findByUsername(auth.getName()).get();
            Device device = deviceRepository.findByDeviceId(deviceId);
            record.setUser(userDb);
            record.setImage(bytes);
            record.setImageDisplay(Base64.getEncoder().encodeToString(bytes));
            record.setTimestamp(new Timestamp(System.currentTimeMillis()));
            record.setDevice(device);
            recordRepository.save(record);
            return ResponseEntity.ok(new MessageResponse("Face detection finished!"));
        }

        return ResponseEntity.ok(new MessageResponse("Error occured!"));

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/addDevice")
    public ResponseEntity<?> addDevice(Authentication auth, @Valid @RequestBody DeviceRequest deviceRequest){
        if (deviceRepository.existsByDeviceId(deviceRequest.getDeviceId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: device ID is already in use!"));
        }

        User userDb = userRespository.findByUsername(auth.getName()).get();
        Device device = new Device(
                deviceRequest.getDeviceId(),
                deviceRequest.getLocation(),
                new Timestamp(System.currentTimeMillis()),
                userDb
        );
        deviceRepository.save(device);
        return ResponseEntity.badRequest().body(new MessageResponse("Device successfully added!"));
    }

    @GetMapping(value = "/devices")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Device>> getDevices(Authentication auth){
        User userDb = userRespository.findByUsername(auth.getName()).get();
        Collection<Device> devices = deviceRepository.findByUser(userDb);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping(value = "/records/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Record>> getRecordsForDevice(@PathVariable String id){
        Device device = deviceRepository.findByDeviceId(id);
        Collection<Record> records= recordRepository.findByDevice(device);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

}
