package mahi.labs.monkeystore.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long UserID;
    @Column
    private String userName;
    @Column
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Cart cart;


    @Column
    private String phone;

    @Column
    private String email;
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    @Column
    private String roles = "ROLE_USER";
    public User() {
        this.active = true;
        this.roles = "ROLE_USER";
    }

    public Long getId() {
        return UserID;
    }

    public void setId(Long id) {
        this.UserID = id;
    }

}