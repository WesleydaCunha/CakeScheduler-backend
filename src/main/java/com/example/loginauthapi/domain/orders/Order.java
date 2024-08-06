package com.example.loginauthapi.domain.orders;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.domain.cake.Filling;
import com.example.loginauthapi.domain.cake.PaymentMethod;
import com.example.loginauthapi.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate; // Certifique-se de usar esse nome

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Certifique-se de usar "user_id"
    private User user;

    @ManyToOne
    @JoinColumn(name = "cake_model_id", nullable = false) // Certifique-se de usar "cake_model_id"
    private CakeModel cakeModel;

    @ManyToMany
    @JoinTable(
            name = "order_complements",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "complement_id")
    )
    private Set<Complement> complements;

    @ManyToMany
    @JoinTable(
            name = "order_fillings",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "filling_id")
    )
    private Set<Filling> fillings;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false) // Certifique-se de usar "payment_method_id"
    private PaymentMethod paymentMethod;

    @Column(name = "observation_client", nullable = true)
    private String observation_client;

    @Column(name = "observation_employee")
    private String obvsevation_employee;

    @Column(name = "total_value", nullable = false)
    private Double totalValue; // Certifique-se de usar esse nome

    @Column(name = "order_status")
    private String orderStatus; // Certifique-se de usar esse nome


}
