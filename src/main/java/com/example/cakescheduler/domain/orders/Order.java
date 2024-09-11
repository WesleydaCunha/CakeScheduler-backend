package com.example.cakescheduler.domain.orders;

import com.example.cakescheduler.domain.cake.CakeModel;
import com.example.cakescheduler.domain.cake.Complement;
import com.example.cakescheduler.domain.cake.Filling;
import com.example.cakescheduler.domain.cake.PaymentMethod;
import com.example.cakescheduler.domain.user.UserClient;
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
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserClient userClient;

    @ManyToOne
    @JoinColumn(name = "cake_model_id", nullable = false)
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
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "observation_client")
    private String observation_client;

    @Column(name = "observation_employee")
    private String observation_employee;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @Column(name = "order_status")
    private String orderStatus;

}
