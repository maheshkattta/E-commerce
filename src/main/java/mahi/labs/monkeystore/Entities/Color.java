package mahi.labs.monkeystore.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "colors")
public class Color {

    @Id
    @Column(name = "color_id")
    private int colorId;

    @Column(name = "color_name")
    private String colorName;

    // Constructors, getters, setters...

    public Color() {
    }

    public Color(int colorId, String colorName) {
        this.colorId = colorId;
        this.colorName = colorName;
    }

}
