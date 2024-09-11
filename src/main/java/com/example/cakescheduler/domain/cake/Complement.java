package com.example.cakescheduler.domain.cake;

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
    @Column(nullable = false, length = 2048)
    private String image_url;
    @Column(nullable = false)
    private Double price;

    public void setComplement_name(String complement_name) {
        this.complement_name = complement_name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUrl(String image_url) {
        this.image_url = image_url;
    }
}