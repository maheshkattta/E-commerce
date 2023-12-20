package mahi.labs.monkeystore.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderTable order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Outfit product;
    private int quantity;
    private BigDecimal price;

}
