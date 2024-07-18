package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.domain.cake.Filling;
import com.example.loginauthapi.domain.cake.PaymentMethod;
import com.example.loginauthapi.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cake")
@RequiredArgsConstructor
public class CakeController {
    private final CakeService cakeService;

    // Endpoints para gerenciar modelos de bolo
    @GetMapping("/models")
    public ResponseEntity<List<CakeModel>> findAllCakeModels() {
        List<CakeModel> cakeModels = cakeService.findAllCakeModels();
        return ResponseEntity.ok(cakeModels);
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<CakeModel> findCakeModelById(@PathVariable Long id) {
        CakeModel cakeModel = cakeService.findCakeModelById(id);
        return ResponseEntity.ok(cakeModel);
    }

    @PostMapping("/models")
    public ResponseEntity<CakeModel> saveCakeModel(@RequestBody CakeModel cakeModel) {
        CakeModel savedCakeModel = cakeService.saveCakeModel(cakeModel);
        return ResponseEntity.ok(savedCakeModel);
    }

    @DeleteMapping("/models/{id}")
    public ResponseEntity<Void> deleteCakeModel(@PathVariable Long id) {
        cakeService.deleteCakeModel(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para gerenciar recheios
    @GetMapping("/fillings")
    public ResponseEntity<List<Filling>> findAllFillings() {
        List<Filling> fillings = cakeService.findAllFillings();
        return ResponseEntity.ok(fillings);
    }

    @GetMapping("/fillings/{id}")
    public ResponseEntity<Filling> findFillingById(@PathVariable Long id) {
        Filling filling = cakeService.findFillingById(id);
        return ResponseEntity.ok(filling);
    }

    @PostMapping("/fillings")
    public ResponseEntity<Filling> saveFilling(@RequestBody Filling filling) {
        Filling savedFilling = cakeService.saveFilling(filling);
        return ResponseEntity.ok(savedFilling);
    }

    @DeleteMapping("/fillings/{id}")
    public ResponseEntity<Void> deleteFilling(@PathVariable Long id) {
        cakeService.deleteFilling(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para gerenciar complementos
    @GetMapping("/complements")
    public ResponseEntity<List<Complement>> findAllComplements() {
        List<Complement> complements = cakeService.findAllComplements();
        return ResponseEntity.ok(complements);
    }

    @GetMapping("/complements/{id}")
    public ResponseEntity<Complement> findComplementById(@PathVariable Long id) {
        Complement complement = cakeService.findComplementById(id);
        return ResponseEntity.ok(complement);
    }

    @PostMapping("/complements")
    public ResponseEntity<Complement> saveComplement(@RequestBody Complement complement) {
        Complement savedComplement = cakeService.saveComplement(complement);
        return ResponseEntity.ok(savedComplement);
    }

    @DeleteMapping("/complements/{id}")
    public ResponseEntity<Void> deleteComplement(@PathVariable Long id) {
        cakeService.deleteComplement(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para gerenciar formas de pagamento
    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethod>> findAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = cakeService.findAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/payment-methods/{id}")
    public ResponseEntity<PaymentMethod> findPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = cakeService.findPaymentMethodById(id);
        return ResponseEntity.ok(paymentMethod);
    }

    @PostMapping("/payment-methods")
    public ResponseEntity<PaymentMethod> savePaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        PaymentMethod savedPaymentMethod = cakeService.savePaymentMethod(paymentMethod);
        return ResponseEntity.ok(savedPaymentMethod);
    }

    @DeleteMapping("/payment-methods/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        cakeService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}