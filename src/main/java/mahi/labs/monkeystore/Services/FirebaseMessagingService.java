package mahi.labs.monkeystore.Services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import mahi.labs.monkeystore.Requests.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseMessagingService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseMessagingService.class);

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessage notificationMessage) {
        try {
            logger.info("Sending notification to token: {}", notificationMessage.getName());

            Notification notification = Notification.builder()
                    .setTitle(notificationMessage.getTitle())
                    .setBody(notificationMessage.getBody())
                    .build();
            Message message = Message
                    .builder()
                    .setToken(notificationMessage.getRecipientToken())
                    .setNotification(notification)
                    .putAllData(notificationMessage.getData())
                    .build();

            firebaseMessaging.send(message);

            logger.info("Notification sent successfully");
            return "Successfully sent message";
        } catch (FirebaseMessagingException e) {
            logger.error("Error sending notification: {}", e.getMessage());
            return "Error sending message";
        }
    }
}
