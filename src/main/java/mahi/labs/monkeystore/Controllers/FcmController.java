package mahi.labs.monkeystore.Controllers;

import mahi.labs.monkeystore.Requests.NotificationMessage;
import mahi.labs.monkeystore.Services.FirebaseMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class FcmController {

    private static final Logger logger = LoggerFactory.getLogger(FcmController.class);

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody NotificationMessage notificationMessage) {
        try {
            int otp = generateOTP();
            notificationMessage.setBody(notificationMessage.getBody() + "\n");
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("phone", notificationMessage.getData().get("phone"));
            dataMap.put("otp", String.valueOf(otp));
            notificationMessage.setData(dataMap);
            return firebaseMessagingService.sendNotificationByToken(notificationMessage);
        } catch (Exception e) {
            logger.error("Error sending notification: {}", e.getMessage());
            return "Error sending notification";
        }
    }

    private static int generateOTP() {
        try {
            // Define the range of the 6-digit number (from 100000 to 999999)
            int min = 100000;
            int max = 999999;

            // Create an instance of the Random class
            Random random = new Random();

            // Generate a random number within the specified range
            return random.nextInt((max - min) + 1) + min;
        } catch (Exception e) {
            logger.error("Error generating OTP: {}", e.getMessage());
            throw new RuntimeException("Error generating OTP", e);
        }
    }
}
