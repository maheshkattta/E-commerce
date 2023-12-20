package mahi.labs.monkeystore.Services;

import jakarta.persistence.EntityNotFoundException;
import mahi.labs.monkeystore.Entities.*;
import mahi.labs.monkeystore.Repositories.CartRepository;
import mahi.labs.monkeystore.Repositories.OrderDetailRepository;
import mahi.labs.monkeystore.Repositories.OrderRepository;
import mahi.labs.monkeystore.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public void placeOrder(Long userId) {
        try {
            logger.info("Placing order for user with id: {}", userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

            Cart cart = user.getCart();
            if (cart == null || cart.getCartItems().isEmpty()) {
                throw new IllegalStateException("Cannot place an order with an empty cart");
            }

            OrderTable order = new OrderTable();
            order.setUser(user);
            order.setOrderDate(LocalDate.now());

            BigDecimal totalAmount = calculateTotalAmount(cart.getCartItems());
            order.setTotalAmount(totalAmount);

            order = orderRepository.save(order);

            for (CartItem cartItem : cart.getCartItems()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(cartItem.getProduct().getPrice());
                orderDetailRepository.save(orderDetail);
            }

            // Clear the cart after placing the order
            cart.getCartItems().clear();
            cartRepository.save(cart);

            logger.info("Order placed successfully");
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error placing order: {}", e.getMessage());
            throw new RuntimeException("Failed to place order", e);
        }
    }

    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
