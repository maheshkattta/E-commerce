package mahi.labs.monkeystore.Controllers;

import mahi.labs.monkeystore.Entities.User;
import mahi.labs.monkeystore.Repositories.UserRepository;
import mahi.labs.monkeystore.Requests.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome user";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            if (userRepository.existsByUserName(request.getUsername())) {
                return ResponseEntity.ok("User already exists!");
            }

            // Validate the request and perform user registration
            User user = new User();
            user.setRoles("ROLE_USER");
            user.setUserName(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            userRepository.save(user);

            logger.info("User registered successfully. Username: {}", request.getUsername());
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error registering user");
        }
    }

    @PostMapping("/update-details")
    public ResponseEntity<String> updateDetails(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String email) {
        try {
            if (userRepository.existsByUserName(username)) {
                Optional<User> userOptional = userRepository.findByUserName(username);
                User user = userOptional.orElse(null);

                if (user != null) {
                    if (phone != null) {
                        user.setPhone(phone);
                    }
                    if (password != null) {
                        user.setPassword(passwordEncoder.encode(password));
                    }
                    if (email != null) {
                        user.setEmail(email);
                    }

                    userRepository.save(user);
                    logger.info("User details updated successfully. Username: {}", username);
                    return ResponseEntity.ok("User details updated successfully!");
                } else {
                    return ResponseEntity.ok("User not found!");
                }
            } else {
                return ResponseEntity.ok("User not found!");
            }
        } catch (Exception e) {
            logger.error("Error updating user details: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error updating user details");
        }
    }
}
