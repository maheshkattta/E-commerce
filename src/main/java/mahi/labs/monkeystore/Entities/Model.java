package mahi.labs.monkeystore.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int modelId;

    @Column(name = "model_name")
    private String modelName;

    // Constructors, getters, setters...

    public Model() {
    }

    public Model(String modelName) {
        this.modelName = modelName;
    }

}
