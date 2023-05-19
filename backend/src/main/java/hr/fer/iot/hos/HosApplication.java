package hr.fer.iot.hos;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import nu.pattern.OpenCV;

@SpringBootApplication
public class HosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HosApplication.class, args);
        OpenCV.loadLocally();
    }

}
