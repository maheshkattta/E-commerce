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
@Table(name = "designs")
public class Design {

    @Id
    @Column(name = "design_id")
    private int designId;

    @Column(name = "design_name")
    private String designName;

    // Constructors, getters, setters...

    public Design() {
    }

    public Design(int designId, String designName) {
        this.designId = designId;
        this.designName = designName;
    }

}

