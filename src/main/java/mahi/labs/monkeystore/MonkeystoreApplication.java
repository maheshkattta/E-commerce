package mahi.labs.monkeystore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class MonkeystoreApplication {
	@Value("${firebase.credentials.path}")
	private String firebaseCredentialsPath;

	private static final Logger logger = LoggerFactory.getLogger(MonkeystoreApplication.class);

	@Bean
	FirebaseMessaging firebaseMessaging() {
		try {
			String firebaseCredentialsPath = System.getProperty("user.dir") + "/key.json";
			GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(firebaseCredentialsPath));

			FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
			FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "monkeystore");
			return FirebaseMessaging.getInstance(app);
		} catch (IOException e) {
			logger.error("Error initializing FirebaseMessaging: {}", e.getMessage());
			throw new RuntimeException("Failed to initialize FirebaseMessaging", e);
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(MonkeystoreApplication.class, args);
	}

}
