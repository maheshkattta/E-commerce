package mahi.labs.monkeystore.Controllers;

import mahi.labs.monkeystore.Entities.*;
import mahi.labs.monkeystore.Repositories.UserRepository;
import mahi.labs.monkeystore.Requests.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("logincontroller")
    public String loginController() {
        return "This is the login controller";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody RegistrationRequest authRequest) {
        try {
            // Retrieve user by username, phone, or email
            User user = userRepository.findByUserNameOrPhoneOrEmail(
                    authRequest.getUsername(),
                    authRequest.getPhone(),
                    authRequest.getEmail()
            );

            if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                // Authentication successful
                return ResponseEntity.ok("Authentication successful");
            } else {
                // Authentication failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (Exception e) {
            logger.error("Error during authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during authentication");
        }
    }
}
