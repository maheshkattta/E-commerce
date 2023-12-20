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
@Table(name = "occasions")
public class Occasion {

    @Id
    @Column(name = "occasion_id")
    private int occasionId;

    @Column(name = "occasion_name")
    private String occasionName;

    // Constructors, getters, setters...

    public Occasion() {
    }

    public Occasion(int occasionId, String occasionName) {
        this.occasionId = occasionId;
        this.occasionName = occasionName;
    }

}
