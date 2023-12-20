package mahi.labs.monkeystore.Requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartRequest {


    private Long cartId;
    private List<CartItemRequest> cartItems;


}


