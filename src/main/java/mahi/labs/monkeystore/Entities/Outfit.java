package mahi.labs.monkeystore.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "outfits")
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Long productId;

    @Column(name = "productname")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "size")
    private String size;

    @Column(name = "offer")
    private String offer;

    @Column(precision = 10, scale = 2)  // Adjust precision and scale as needed
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "occasion_id")
    private Occasion occasion;

    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;

    // Constructors, getters, setters...

    public Outfit() {
    }

    public Outfit(String productName, Category category, Brand brand, Model model, String size,
                  String offer, BigDecimal price, Color color, Occasion occasion, Design design) {
        this.productName = productName;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.offer = offer;
        this.price = price;
        this.color = color;
        this.occasion = occasion;
        this.design = design;
    }

}

