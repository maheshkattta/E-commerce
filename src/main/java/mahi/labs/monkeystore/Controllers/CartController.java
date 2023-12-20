package mahi.labs.monkeystore.Controllers;

import mahi.labs.monkeystore.Entities.Cart;
import mahi.labs.monkeystore.Entities.CartItem;
import mahi.labs.monkeystore.Requests.CartItemRequest;
import mahi.labs.monkeystore.Requests.CartRequest;
import mahi.labs.monkeystore.Services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getCartByUserId")
    public ResponseEntity<CartRequest> getCartByUserId(@RequestParam Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            CartRequest cartDTO = convertToRequest(cart);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting cart by user ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addItemToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            cartService.addItemToCart(userId, productId, quantity);
            logger.info("Item added to cart successfully. User ID: {}, Product ID: {}, Quantity: {}", userId, productId, quantity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding item to cart: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/decrease")
    public ResponseEntity<Void> decreaseItemFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        try {
            cartService.decreaseItemFromcart(userId, productId, 1);
            logger.info("Item quantity decreased successfully. User ID: {}, Product ID: {}", userId, productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error decreasing item quantity from cart: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        try {
            cartService.removeItemFromCart(userId, productId);
            logger.info("Item removed from cart successfully. User ID: {}, Product ID: {}", userId, productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error removing item from cart: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private CartRequest convertToRequest(Cart cart) {
        CartRequest cartDTO = new CartRequest();
        cartDTO.setCartId(cart.getCartId());

        List<CartItemRequest> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemRequest cartItemDTO = new CartItemRequest();
            cartItemDTO.setCartItemId(cartItem.getCartItemId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setProductId(cartItem.getProduct().getProductId());
            cartItemDTOs.add(cartItemDTO);
        }

        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }
}
