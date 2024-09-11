package com.example.cakescheduler.controllers;

import com.example.cakescheduler.domain.cake.CakeModel;
import com.example.cakescheduler.domain.cake.Complement;
import com.example.cakescheduler.domain.cake.Filling;
import com.example.cakescheduler.domain.cake.PaymentMethod;
import com.example.cakescheduler.domain.orders.Order;
import com.example.cakescheduler.domain.user.UserClient;
import com.example.cakescheduler.dto.OrderRequestDTO;
import com.example.cakescheduler.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private UserClientRepository userClientRepository;

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
        Optional<UserClient> user = userClientRepository.findById(body.user());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        // Parse cake model
        Optional<CakeModel> cakeModel = cakeModelRepository.findById(body.cake_model());
        if (cakeModel.isEmpty()) {
            return ResponseEntity.badRequest().body("Modelo de bolo não encontrado.");
        }


        Set<Complement> complements = new HashSet<>();
        for (Long complementId : body.complements()) {
            Optional<Complement> complement = complementRepository.findById(complementId);
            if (complement.isPresent()) {
                complements.add(complement.get());
            } else {
                return ResponseEntity.badRequest().body("Complemento não encontrado: " + complementId);
            }
        }


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
        newOrder.setDeliveryDate(deliveryDate);
        newOrder.setUserClient(user.get());
        newOrder.setCakeModel(cakeModel.get());
        newOrder.setComplements(complements);
        newOrder.setFillings(fillings);
        newOrder.setPaymentMethod(paymentMethod.get());
        newOrder.setObservation_client(body.observation_client());
        newOrder.setTotalValue(calculateTotalValue(newOrder));
        newOrder.setOrderStatus("PENDING");

// Log para verificar os dados antes de salvar
        //System.out.println("Peso: " + newOrder.getWeight());
        //System.out.println("Data de entrega: " + newOrder.getDeliveryDate());
        //System.out.println("Usuário: " + newOrder.getUser().getId());
        //System.out.println("Modelo de bolo: " + newOrder.getCakeModel().getId());
        //System.out.println("Complementos: " + newOrder.getComplements());
        //System.out.println("Recheios: " + newOrder.getFillings());
        //System.out.println("Método de pagamento: " + newOrder.getPaymentMethod().getId());
        //System.out.println("Valor total: " + newOrder.getTotalValue());
        //System.out.println("Status do pedido: " + newOrder.getOrderStatus());

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


    @PatchMapping("/update-status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        order.get().setOrderStatus(status);
        orderRepository.save(order.get());
        return ResponseEntity.ok("Status do pedido atualizado com sucesso.");
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @RequestParam String status) {
        try {
            List<Order> orders;
            if (status != null && !status.isEmpty()) {
                orders = orderRepository.findByOrderStatus(status);
            } else {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Order>> getOrdersByUser(@RequestParam String userId) {
        try {
            Optional<UserClient> user = userClientRepository.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<Order> orders = orderRepository.findByUserClient(user.get());
            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/by-delivery-date")
    public ResponseEntity<List<Order>> getOrdersByDeliveryDateAndStatus(
            @RequestParam String date,
            @RequestParam(required = false) String status) {
        try {
            // Parse o parâmetro de data como LocalDate
            LocalDate deliveryDate = LocalDate.parse(date);

            // Defina o início e o fim do dia
            LocalDateTime startOfDay = deliveryDate.atStartOfDay();
            LocalDateTime endOfDay = deliveryDate.atTime(23, 59, 59);

            // Verifique se o status foi fornecido
            List<Order> orders;
            if (status != null && !status.isEmpty()) {
                // Filtrar por data de entrega e status
                orders = orderRepository.findByDeliveryDateBetweenAndOrderStatus(startOfDay, endOfDay, status);
            } else {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
