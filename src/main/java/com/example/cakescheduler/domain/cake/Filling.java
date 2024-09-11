package com.example.cakescheduler.domain.cake;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fillings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String filling_name;
    @Column(nullable = false)
    private Double pricePerKg;


    public void setFilling_Name(String filling_name) {
        this.filling_name = filling_name;
    }

    public void setPricePerKg(Double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

}