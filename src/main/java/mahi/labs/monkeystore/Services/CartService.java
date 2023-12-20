package mahi.labs.monkeystore.Services;

import jakarta.persistence.EntityNotFoundException;
import mahi.labs.monkeystore.Entities.Cart;
import mahi.labs.monkeystore.Entities.CartItem;
import mahi.labs.monkeystore.Entities.Outfit;
import mahi.labs.monkeystore.Entities.User;
import mahi.labs.monkeystore.Repositories.CartItemRepository;
import mahi.labs.monkeystore.Repositories.CartRepository;
import mahi.labs.monkeystore.Repositories.OutfitRepository;
import mahi.labs.monkeystore.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OutfitRepository outfitsRepository;

    public Cart getCartByUserId(Long userID) {
        return cartRepository.findByUserUserID(userID);
    }

    public void addItemToCart(Long userId, Long productId, int quantity) {
        try {
            logger.info("Adding item to cart for user: {}, product: {}, quantity: {}", userId, productId, quantity);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

            Outfit product = outfitsRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

            Cart cart = user.getCart();
            if (cart == null) {
                logger.info("Creating a new cart for user: {}", userId);
                cart = new Cart();
                cart.setUser(user);
                cart = cartRepository.save(cart);
            }

            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
            if (cartItem == null) {
                logger.info("Creating a new cart item for product: {} in cart: {}", productId, cart.getCartId());
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
            }

            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            logger.info("Updating cart item quantity to: {}", cartItem.getQuantity());
            cartItemRepository.save(cartItem);

            logger.info("Item added successfully to the cart");
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while adding item to cart: {}", e.getMessage());
            throw new RuntimeException("Failed to add item to cart", e);
        }
    }

    public void decreaseItemFromcart(Long userId, Long productId, int quantity) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

            Outfit product = outfitsRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

            Cart cart = user.getCart();
            if (cart != null) {
                CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
                if (cartItem != null) {
                    int updatedQuantity = cartItem.getQuantity() - quantity;
                    if (updatedQuantity <= 0) {
                        // Remove the cart item if the quantity becomes zero or negative
                        cartItemRepository.delete(cartItem);
                    } else {
                        // Update the quantity otherwise
                        cartItem.setQuantity(updatedQuantity);
                        cartItemRepository.save(cartItem);
                    }
                }
            }
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while decreasing item from cart: {}", e.getMessage());
            throw new RuntimeException("Failed to decrease item from cart", e);
        }
    }

    public void removeItemFromCart(Long userId, Long productId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

            Outfit product = outfitsRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

            Cart cart = user.getCart();
            if (cart != null) {
                CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
                if (cartItem != null) {
                    // Remove the entire cart item
                    cartItemRepository.delete(cartItem);
                }
            }
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while removing item from cart: {}", e.getMessage());
            throw new RuntimeException("Failed to remove item from cart", e);
        }
    }
}
