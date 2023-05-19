package hr.fer.iot.hos.controller;

import hr.fer.iot.hos.service.FaceDetectionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FaceController {

    @Autowired
    FaceDetectionService faceDetectionService;
    private static final List<byte[]> records = new ArrayList<>();

    @GetMapping(value = "/records")
    public @ResponseBody List<byte[]> getRecords(){
        return records;
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] postImage(@RequestParam("file") MultipartFile file) {

        byte[] bytes = null;
        try {
            bytes = faceDetectionService.detectFaceApi(file.getBytes()).toImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        records.add(bytes);
        return bytes;

    }
}
