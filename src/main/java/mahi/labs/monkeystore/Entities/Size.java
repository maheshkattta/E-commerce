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
@Table(name = "sizes")
public class Size {

    @Id
    @Column(name = "size_id")
    private int sizeId;

    @Column(name = "size_name")
    private String sizeName;

    // Constructors, getters, setters...

    public Size() {
    }

    public Size(int sizeId, String sizeName) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
    }

}
