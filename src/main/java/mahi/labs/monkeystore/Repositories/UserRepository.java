package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    User findByUserNameOrPhoneOrEmail(String userName, String phone, String email);
    boolean existsByUserName(String username);
}
