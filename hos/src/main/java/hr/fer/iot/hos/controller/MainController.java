//package hr.fer.iot.hos.controller;
//
//import hr.fer.iot.hos.model.DetectionModel;
//import hr.fer.iot.hos.service.DnnProcessor;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.core.Scalar;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller("/api")
//public class MainController {
//
//    DnnProcessor processor;
//    List<DetectionModel> detectObject = new ArrayList<DetectionModel>();
//
//    @GetMapping("/hello")
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }
//
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] postImage(MultipartFile file) throws IOException {
//
//        Mat frame = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_UNCHANGED);
//        detectObject = processor.getObjectsInFrame(frame, false);
//        for (DetectionModel obj : detectObject) {
//            Imgproc.rectangle(frame, obj.getLeftBottom(), obj.getRightTop(), new Scalar(255, 0, 0), 2);
//            Imgproc.putText(frame, obj.getObjectName(), obj.getLeftBottom(), Imgproc.FONT_HERSHEY_PLAIN, 2, new Scalar (255, 255, 255));
//        }
//        return mat2Image(frame);
//
//    }
//
//    private static byte[] mat2Image(Mat frame) {
//        MatOfByte buffer = new MatOfByte();
//        Imgcodecs.imencode(".jpg", frame, buffer);
//        return buffer.toArray();
//    }
//}
