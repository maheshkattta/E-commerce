package mahi.labs.monkeystore.Services;

import mahi.labs.monkeystore.Configurations.MyUserDetails;
import mahi.labs.monkeystore.Entities.User;
import mahi.labs.monkeystore.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            logger.info("Loading user by username: {}", userName);

            Optional<User> user = userRepository.findByUserName(userName);
            user.orElseThrow(() -> new UsernameNotFoundException(userName + ": Not found, kindly register"));

            MyUserDetails userDetails = user.map(MyUserDetails::new).get();

            logger.info("User loaded successfully");
            return userDetails;
        } catch (Exception e) {
            logger.error("Error loading user by username: {}", e.getMessage());
            throw new UsernameNotFoundException("Error loading user by username", e);
        }
    }
}
