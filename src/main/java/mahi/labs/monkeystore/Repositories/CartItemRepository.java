package mahi.labs.monkeystore.Repositories;


import mahi.labs.monkeystore.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

// CartItemRepository.java
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProduct(Cart cart, Outfit product);
}