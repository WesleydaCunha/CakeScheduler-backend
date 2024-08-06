package com.example.loginauthapi.domain.cake;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cake_models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CakeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cake_name;
    @Column(nullable = false)
    private String image;

    public void setCake_name(String cake_name) {
        this.cake_name = cake_name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}