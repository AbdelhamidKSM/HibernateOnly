package org.example.entites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_user")
    private String name;

    @Column(name = "email_user")
    private String email;



    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
