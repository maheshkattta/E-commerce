package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Logger logger = LoggerFactory.getLogger(CartRepository.class);

    @Query("SELECT c FROM Cart c WHERE c.user.UserID = :userId")
    Cart findByUserUserID(@Param("userId") Long userId);

    default Cart findCartByUserIdWithExceptionHandling(Long userId) {
        try {
            return findByUserUserID(userId);
        } catch (Exception e) {
            logger.error("Error while retrieving cart for userId {}: {}", userId, e.getMessage());
            throw e; // Re-throw the exception after logging
        }
    }
}
