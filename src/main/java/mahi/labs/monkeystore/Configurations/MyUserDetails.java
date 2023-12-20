package mahi.labs.monkeystore.Configurations;

import mahi.labs.monkeystore.Entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetails.class);

    private String userName;
    private String password;
    private boolean active;
    private String phone;
    private String email;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        try {
            this.userName = user.getUserName();
            this.phone = user.getPhone();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.active = user.isActive();
            this.authorities = Arrays.stream(user.getRoles().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error creating MyUserDetails: {}", e.getMessage());
            throw new RuntimeException("Error creating MyUserDetails", e);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
