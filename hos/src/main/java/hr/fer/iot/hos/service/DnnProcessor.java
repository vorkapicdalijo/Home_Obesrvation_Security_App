//package hr.fer.iot.hos.service;
//
//import hr.fer.iot.hos.model.DetectionModel;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.dnn.Dnn;
//import org.opencv.dnn.Net;
//import org.opencv.imgproc.Imgproc;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class DnnProcessor {
//    private final static Logger logger = LoggerFactory.getLogger(DnnProcessor.class);
//    private final Net net;
//
//    private final String[] classNames = {"background",
//            "aeroplane", "bicycle", "bird", "boat",
//            "bottle", "bus", "car", "cat", "chair",
//            "cow", "diningtable", "dog", "horse",
//            "motorbike", "person", "pottedplant",
//            "sheep", "sofa", "train", "tvmonitor"};
//
//
//    public DnnProcessor() throws IOException {
//        Resource protoResource = new ClassPathResource("models/MobileNetSSD_deploy.prototxt");
//        Resource modelResource = new ClassPathResource("models/MobileNetSSD_deploy.caffemodel");
//        this.net = Dnn.readNetFromCaffe(protoResource.getFile().getAbsolutePath(), modelResource.getFile().getAbsolutePath());
//    }
//
//    public List<DetectionModel> getObjectsInFrame(Mat frame, boolean isGrayFrame) {
//
//        int inWidth = 320;
//        int inHeight = 240;
//        double inScaleFactor = 0.007843;
//        double thresholdDnn =  0.2;
//        double meanVal = 127.5;
//
//        Mat blob;
//        Mat detections;
//        List<DetectionModel> objectList = new ArrayList<>();
//
//        int cols = frame.cols();
//        int rows = frame.rows();
//
//        try {
//            if (isGrayFrame)
//                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_GRAY2RGB);
//
//            blob = Dnn.blobFromImage(frame, inScaleFactor,
//                    new Size(inWidth, inHeight),
//                    new Scalar(meanVal, meanVal, meanVal),
//                    false, false);
//
//            net.setInput(blob);
//            detections = net.forward();
//            detections = detections.reshape(1, (int) detections.total() / 7);
//
//            logger.info("New detection: ");
//            //all detected objects
//            for (int i = 0; i < detections.rows(); ++i) {
//                double confidence = detections.get(i, 2)[0];
//
//                if (confidence < thresholdDnn)
//                    continue;
//
//                int classId = (int) detections.get(i, 1)[0];
//
//                //calculate position of object
//                int xLeftBottom = (int) (detections.get(i, 3)[0] * cols);
//                int yLeftBottom = (int) (detections.get(i, 4)[0] * rows);
//                Point leftPosition = new Point(xLeftBottom, yLeftBottom);
//
//                int xRightTop = (int) (detections.get(i, 5)[0] * cols);
//                int yRightTop = (int) (detections.get(i, 6)[0] * rows);
//                Point rightPosition = new Point(xRightTop, yRightTop);
//
//                float centerX = ( (float) xLeftBottom + (float) xRightTop) / 2;
//                float centerY = ( (float) yLeftBottom - (float) yRightTop) / 2;
//                Point centerPoint = new Point(centerX, centerY);
//
//                logger.info(classNames[classId]);
//
//                DetectionModel dnnObject = new DetectionModel(classId, classNames[classId], leftPosition, rightPosition, centerPoint);
//                objectList.add(dnnObject);
//            }
//
//        } catch (Exception ex) {
//            logger.error("An error occurred DNN: ", ex);
//        }
//        return objectList;
//    }
//}
