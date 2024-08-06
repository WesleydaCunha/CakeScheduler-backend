package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.domain.cake.Filling;
import com.example.loginauthapi.domain.cake.PaymentMethod;
import com.example.loginauthapi.domain.orders.Order;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dto.OrderRequestDTO;
import com.example.loginauthapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CakeModelRepository cakeModelRepository;

    @Autowired
    private ComplementRepository complementRepository;

    @Autowired
    private FillingRepository fillingRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerOrder(@RequestBody OrderRequestDTO body) {
        // Parse user
        Optional<User> user = userRepository.findById(body.user());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        // Parse cake model
        Optional<CakeModel> cakeModel = cakeModelRepository.findById(body.cake_model());
        if (cakeModel.isEmpty()) {
            return ResponseEntity.badRequest().body("Modelo de bolo não encontrado.");
        }

        // Parse complements
        Set<Complement> complements = new HashSet<>();
        for (Long complementId : body.complements()) {
            Optional<Complement> complement = complementRepository.findById(complementId);
            if (complement.isPresent()) {
                complements.add(complement.get());
            } else {
                return ResponseEntity.badRequest().body("Complemento não encontrado: " + complementId);
            }
        }

        // Parse fillings
        Set<Filling> fillings = new HashSet<>();
        for (Long fillingId : body.fillings()) {
            Optional<Filling> filling = fillingRepository.findById(fillingId);
            if (filling.isPresent()) {
                fillings.add(filling.get());
            } else {
                return ResponseEntity.badRequest().body("Recheio não encontrado: " + fillingId);
            }
        }

        // Parse payment method
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(body.payment_method());
        if (paymentMethod.isEmpty()) {
            return ResponseEntity.badRequest().body("Método de pagamento não encontrado.");
        }

        // Parse delivery date
        LocalDateTime deliveryDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            deliveryDate = LocalDateTime.parse(body.delivery_date(), formatter);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Formato de data de entrega inválido.");
        }

        // Create and save order
        Order newOrder = new Order();
        newOrder.setWeight(body.weight());
        newOrder.setDeliveryDate(deliveryDate); // Verifique se o nome está correto
        newOrder.setUser(user.get());
        newOrder.setCakeModel(cakeModel.get()); // Verifique se o nome está correto
        newOrder.setComplements(complements);
        newOrder.setFillings(fillings);
        newOrder.setPaymentMethod(paymentMethod.get()); // Verifique se o nome está correto
        newOrder.setTotalValue(calculateTotalValue(newOrder)); // Verifique se o nome está correto
        newOrder.setOrderStatus("PENDING"); // Verifique se o nome está correto

// Log para verificar os dados antes de salvar
        System.out.println("Peso: " + newOrder.getWeight());
        System.out.println("Data de entrega: " + newOrder.getDeliveryDate()); // Verifique se o nome está correto
        System.out.println("Usuário: " + newOrder.getUser().getId());
        System.out.println("Modelo de bolo: " + newOrder.getCakeModel().getId()); // Verifique se o nome está correto
        System.out.println("Complementos: " + newOrder.getComplements());
        System.out.println("Recheios: " + newOrder.getFillings());
        System.out.println("Método de pagamento: " + newOrder.getPaymentMethod().getId()); // Verifique se o nome está correto
        System.out.println("Valor total: " + newOrder.getTotalValue()); // Verifique se o nome está correto
        System.out.println("Status do pedido: " + newOrder.getOrderStatus()); // Verifique se o nome está correto

        orderRepository.save(newOrder);
        return ResponseEntity.ok("Pedido criado com sucesso!");

    }

    // Implement a method to calculate the total value of the order
    private Double calculateTotalValue(Order order) {
        double totalValue = 0.0;

        // Encontrar o valor do maior recheio
        double maxFillingPrice = order.getFillings().stream()
                .mapToDouble(Filling::getPricePerKg)
                .max()
                .orElse(0.0);

        // Calcular o valor total dos complementos
        double totalComplementValue = order.getComplements().stream()
                .mapToDouble(Complement::getPrice)
                .sum();

        // Calcular o valor total do pedido
        totalValue = (maxFillingPrice * order.getWeight()) + totalComplementValue;

        return totalValue;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order.get());
    }

}
