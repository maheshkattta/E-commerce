package mahi.labs.monkeystore.Requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest {
    private Long cartItemId;
    private int quantity;
    private Long productId;

}
