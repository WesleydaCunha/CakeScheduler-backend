package com.example.loginauthapi.domain.cake;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "complements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Complement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String complement_name;
    @Column(nullable = false)
    private Double price;

    public void setComplement_name(String complement_name) {
        this.complement_name = complement_name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}