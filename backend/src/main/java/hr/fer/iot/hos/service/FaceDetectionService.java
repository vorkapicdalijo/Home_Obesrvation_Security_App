package hr.fer.iot.hos.service;

import hr.fer.iot.hos.model.FaceModel;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FaceDetectionService {

    private final static Logger logger = LoggerFactory.getLogger(FaceDetectionService.class);
    private List<FaceModel> faceEntities;
    private Mat image;

    public FaceDetectionService detectFaceApi(byte[] fileBytes) throws IOException {
        faceEntities=new ArrayList<>();
        MatOfRect faceDetections = new MatOfRect();
        Resource haarResource = new ClassPathResource("haarcascades/haarcascade_frontalface_alt.xml");
        CascadeClassifier faceDetector = new CascadeClassifier(haarResource.getFile().getAbsolutePath());

        image = Imgcodecs.imdecode(new MatOfByte(fileBytes), Imgcodecs.IMREAD_UNCHANGED);
        faceDetector.detectMultiScale(image, faceDetections);

        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            faceEntities.add(new FaceModel(rect.x, rect.y, rect.width, rect.height, 0));
        }
        return this;
    }

    public byte[] toImage() {
        for (FaceModel fc : faceEntities) {
            Imgproc.rectangle(image, new Point(fc.getX(), fc.getY()), new Point(fc.getX() + fc.getWidth(), fc.getY() + fc.getHeight()), new Scalar(0, 255, 0));
        }
        return mat2Image(image);
    }

    private byte[] mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }

}
