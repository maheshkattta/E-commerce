package mahi.labs.monkeystore.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private int brandId;

    @Column(name = "brand_name")
    private String brandName;

    // Constructors, getters, setters...

    public Brand() {
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

}
