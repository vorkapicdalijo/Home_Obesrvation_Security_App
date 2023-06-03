package hr.fer.iot.hos.controller;

import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.DeviceRequest;
import hr.fer.iot.hos.model.payload.MessageResponse;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
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
        for (Record r : records) {
            r.setImageDisplay(Base64.getEncoder().encodeToString(r.getImage()));
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
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

        return ResponseEntity.ok(new MessageResponse("Device successfully added!"));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/deleteDevice")
    @Transactional
    public ResponseEntity<?> deleteDevice(@RequestParam("device_id") String id) {
        if (deviceRepository.existsByDeviceId(id)) {
            recordRepository.deleteRecordsByDeviceId(id);
            deviceRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Device successfully deleted!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device with this ID not found");
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/deleteRecord/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        if (recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Record deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record with this ID not found");
    }


    @GetMapping(value = "/devices")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Device>> getDevices(Authentication auth) {
        User userDb = userRespository.findByUsername(auth.getName()).get();
        Collection<Device> devices = deviceRepository.findByUser(userDb);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping(value = "/device/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getRecordsForDevice(@PathVariable String id, Authentication auth) {
        Device device = deviceRepository.findByDeviceId(id);
        User userDb = userRespository.findByUsername(auth.getName()).get();
        if (!userDb.getUsername().equals(device.getUser().getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Access denied!"));
        }
        Collection<Record> records = recordRepository.findByDevice(device);
        for (Record r : records) {
            r.setImageDisplay(Base64.getEncoder().encodeToString(r.getImage()));
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping(value = "/records/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getRecordForId(@PathVariable Long id, Authentication auth) {
        Optional<Record> record = recordRepository.findById(id);
        User userDb = userRespository.findByUsername(auth.getName()).get();
        if (record.isPresent()) {
            if (!userDb.getUsername().equals(record.get().getUser().getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Access denied!"));
            }
            record.get().setImageDisplay(Base64.getEncoder().encodeToString(record.get().getImage()));
            return new ResponseEntity<>(record.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("No record!"));
        }
    }

}
