package org.example.relationshipsentities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContactDetails {
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

}