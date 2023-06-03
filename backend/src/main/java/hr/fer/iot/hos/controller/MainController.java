package hr.fer.iot.hos.controller;

import hr.fer.iot.hos.model.Device;
import hr.fer.iot.hos.model.Record;
import hr.fer.iot.hos.model.User;
import hr.fer.iot.hos.model.payload.DeviceRequest;
import hr.fer.iot.hos.model.payload.MessageResponse;
import hr.fer.iot.hos.model.payload.PlatformRequest;
import hr.fer.iot.hos.repository.DeviceRepository;
import hr.fer.iot.hos.repository.RecordRepository;
import hr.fer.iot.hos.repository.UserRespository;
import hr.fer.iot.hos.security.notsecurity.TrustAllClientHttpRequestFactory;
import hr.fer.iot.hos.service.FaceDetectionService;
import hr.fer.iot.hos.service.FirebaseMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
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

    @Value("${backend.app.platformPostUrl}")
    String url;

    @Value("${backend.app.truststore.password}")
    String truststorePassword;

    @GetMapping(value = "/records")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Collection<Record>> getRecords(Authentication auth) {
        User userDb = userRespository.findByUsername(auth.getName()).get();
        Collection<Record> records = recordRepository.findByUser(userDb);
        for (Record r : records) {
            r.setImageDisplay(Base64.getEncoder().encodeToString(r.getImage()));
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    private void postDeviceDataToPlatform(String deviceId, Long userId) {
//            Add truststore with selfsigned cert to trust
//            InputStream trustSotreFile = new ClassPathResource("certs/truststore.jks").getInputStream();
//            KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
//            truststore.load(trustSotreFile, truststorePassword.toCharArray());
//
//            // Create a TrustManagerFactory using the truststore
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(truststore);
//
//            // Create an SSLContext and initialize it with the TrustManagerFactory
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
//
//            // Configure the RestTemplate to use the custom SSLContext
//            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//            requestFactory.setConnectTimeout(5000);
//            requestFactory.setReadTimeout(5000);

        // In this mode now app trust all certs - this is not good but my colleagues are lazy to upload valid cert,
        // or properly generated selfsigned cert :(
        RestTemplate restTemplate = new RestTemplate(new TrustAllClientHttpRequestFactory());
        PlatformRequest request = new PlatformRequest(deviceId, String.valueOf(userId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlatformRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> result = restTemplate.postForEntity(url, httpEntity, String.class);

        logger.info(String.valueOf(result.getStatusCodeValue()));
        logger.info(result.getBody());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/addDevice")
    public ResponseEntity<?> addDevice(Authentication auth, @Valid @RequestBody DeviceRequest deviceRequest) {
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
        try {
            postDeviceDataToPlatform(device.getDeviceId(), device.getUser().getId());
        } catch (RestClientException e) {
            logger.error("Error while uploading data to platform {}", e.getMessage(), e);
        }

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
