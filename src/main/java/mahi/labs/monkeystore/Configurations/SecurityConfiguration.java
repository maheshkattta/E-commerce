package mahi.labs.monkeystore.Configurations;

import mahi.labs.monkeystore.Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider(MyUserDetailsService customDatabaseUserDetailsService) {
        try {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(customDatabaseUserDetailsService);
            provider.setPasswordEncoder(new BCryptPasswordEncoder());
            return provider;
        } catch (Exception e) {
            logger.error("Error creating DaoAuthenticationProvider: {}", e.getMessage());
            throw new RuntimeException("Error creating DaoAuthenticationProvider", e);
        }
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            http.csrf(AbstractHttpConfigurer::disable);
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/*").hasRole("ADMIN")
                    .requestMatchers("/user/*", "/auth/authenticate").permitAll()
                    .anyRequest().authenticated());
            http.httpBasic(Customizer.withDefaults());

            return http.build();
        } catch (Exception e) {
            logger.error("Error configuring security filter chain: {}", e.getMessage());
            throw new RuntimeException("Error configuring security filter chain", e);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
