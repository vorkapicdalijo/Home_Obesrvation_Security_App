package hr.fer.iot.hos.service;

import com.google.firebase.messaging.*;
import hr.fer.iot.hos.model.AppNotification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotificationTopic(AppNotification note, String topic) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getTitle())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();

        Message message = Message
                .builder()
                .setTopic(topic)
                .setNotification(notification)
                .build();

        return firebaseMessaging.send(message);
    }

    public String sendNotificationToken(AppNotification note, String token, String redirectLink) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getTitle())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();

        WebpushFcmOptions webpushFcmOptions = WebpushFcmOptions
                .builder()
                .setLink(redirectLink)
                .build();

        WebpushConfig webpushConfig = WebpushConfig
                .builder()
                .setFcmOptions(webpushFcmOptions)
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .setWebpushConfig(webpushConfig)
                .putData("link", redirectLink)
                .build();

        return firebaseMessaging.send(message);
    }
}
