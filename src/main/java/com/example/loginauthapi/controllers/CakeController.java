package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.domain.cake.Filling;
import com.example.loginauthapi.domain.cake.PaymentMethod;
import com.example.loginauthapi.dto.CakeModelRequestDTO;
import com.example.loginauthapi.dto.ComplementRequestDTO;
import com.example.loginauthapi.dto.FillingRequestDTO;
import com.example.loginauthapi.dto.PaymentMethodRequestDTO;
import com.example.loginauthapi.repositories.CakeModelRepository;
import com.example.loginauthapi.repositories.ComplementRepository;
import com.example.loginauthapi.repositories.FillingRepository;
import com.example.loginauthapi.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cake")

public class CakeController {
    @Autowired
    private CakeModelRepository cakeModelRepository;
    @Autowired
    private ComplementRepository complementRepository;
    @Autowired
    private FillingRepository fillingRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;



    @GetMapping("/models")
    public ResponseEntity<List<CakeModel>> getAllCakeModels() {
        List<CakeModel> listModels = cakeModelRepository.findAll();
        return ResponseEntity.ok(listModels);
    }


    @PostMapping("/models/register")
    public ResponseEntity<String> saveCakeModel(@RequestBody CakeModelRequestDTO body) {
        CakeModel newCakeModel = new CakeModel();
        newCakeModel.setCake_name(body.cake_name());
        newCakeModel.setImage(body.image());
        this.cakeModelRepository.save(newCakeModel);
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<CakeModel> getCakeModelsDetails(@PathVariable Long id) {
        Optional<CakeModel> cakeModel = this.cakeModelRepository.findById(id);
        return cakeModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/models/{id}")
    public ResponseEntity<String> deleteCakeModel(@PathVariable Long id) {
        Optional<CakeModel> existingCakeModel = cakeModelRepository.findById(id);

        if (existingCakeModel.isPresent()) {
            cakeModelRepository.delete(existingCakeModel.get());
            return ResponseEntity.ok("Modelo de bolo deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/models/{id}")
    public ResponseEntity<String> updateCakeModel(@PathVariable Long id, @RequestBody CakeModelRequestDTO body) {
        Optional<CakeModel> existingCakeModel = cakeModelRepository.findById(id);

        if (existingCakeModel.isPresent()) {
            CakeModel cakeModel = existingCakeModel.get();
            cakeModel.setCake_name(body.cake_name());
            cakeModel.setImage(body.image());
            cakeModelRepository.save(cakeModel);
            return ResponseEntity.ok("Modelo de bolo atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/complement/register")
    public ResponseEntity<String> registerCakeComplement(@RequestBody ComplementRequestDTO body) {
        Complement newComplement = new Complement();
        newComplement.setComplement_name(body.complement_name());
        newComplement.setPrice(body.price());
        this.complementRepository.save(newComplement);
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/complements")
    public ResponseEntity<List<Complement>> getAllComplements() {
        List<Complement> listComplements = complementRepository.findAll();
        return ResponseEntity.ok(listComplements);
    }

    @GetMapping("/complements/{id}")
    public ResponseEntity<Complement> getComplementDetails(@PathVariable Long id) {
        Optional<Complement> complement = this.complementRepository.findById(id);
        return complement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/complements/{id}")
    public ResponseEntity<String> updateComplement(@PathVariable Long id, @RequestBody ComplementRequestDTO body) {
        Optional<Complement> existingComplement = complementRepository.findById(id);

        if (existingComplement.isPresent()) {
            Complement complement = existingComplement.get();
            complement.setComplement_name(body.complement_name());
            complement.setPrice(body.price());
            complementRepository.save(complement);
            return ResponseEntity.ok("Complemento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/complements/{id}")
    public ResponseEntity<String> deleteComplement(@PathVariable Long id) {
        Optional<Complement> existingComplement = complementRepository.findById(id);

        if (existingComplement.isPresent()) {
            complementRepository.delete(existingComplement.get());
            return ResponseEntity.ok("Complemento deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/fillings/register")
    public ResponseEntity<String> registerFilling(@RequestBody FillingRequestDTO body) {
        Filling newFilling = new Filling();
        newFilling.setFilling_Name(body.filling_name());
        newFilling.setPricePerKg(body.pricePerKg());
        this.fillingRepository.save(newFilling);
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/fillings")
    public ResponseEntity<List<Filling>> getAllFillings() {
        List<Filling> listFillings = fillingRepository.findAll();
        return ResponseEntity.ok(listFillings);
    }

    @GetMapping("/fillings/{id}")
    public ResponseEntity<Filling> getFillingDetails(@PathVariable Long id) {
        Optional<Filling> filling = this.fillingRepository.findById(id);
        return filling.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/fillings/{id}")
    public ResponseEntity<String> updateFilling(@PathVariable Long id, @RequestBody FillingRequestDTO body) {
        Optional<Filling> existingFilling = fillingRepository.findById(id);

        if (existingFilling.isPresent()) {
            Filling filling = existingFilling.get();
            filling.setFilling_Name(body.filling_name());
            filling.setPricePerKg(body.pricePerKg());
            fillingRepository.save(filling);
            return ResponseEntity.ok("Recheio atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/fillings/{id}")
    public ResponseEntity<String> deleteFilling(@PathVariable Long id) {
        Optional<Filling> existingFilling = fillingRepository.findById(id);

        if (existingFilling.isPresent()) {
            fillingRepository.delete(existingFilling.get());
            return ResponseEntity.ok("Recheio deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/payment-methods/register")
    public ResponseEntity<String> registerPaymentMethods(@RequestBody PaymentMethodRequestDTO body) {
        PaymentMethod newPaymentMethod = new PaymentMethod();
        newPaymentMethod.setPayment_type(body.payment_type());
        this.paymentMethodRepository.save(newPaymentMethod);
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> listPaymentMethods = paymentMethodRepository.findAll();
        return ResponseEntity.ok(listPaymentMethods);
    }

    @GetMapping("/payment-methods/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodDetails(@PathVariable Long id) {
        Optional<PaymentMethod> paymentMethod = this.paymentMethodRepository.findById(id);
        return paymentMethod.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/payment-methods/{id}")
    public ResponseEntity<String> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodRequestDTO body) {
        Optional<PaymentMethod> existingPaymentMethod = paymentMethodRepository.findById(id);

        if (existingPaymentMethod.isPresent()) {
            PaymentMethod paymentMethod = existingPaymentMethod.get();
            paymentMethod.setPayment_type(body.payment_type());
            paymentMethodRepository.save(paymentMethod);
            return ResponseEntity.ok("Método de pagamento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/payment-methods/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable Long id) {
        Optional<PaymentMethod> existingPaymentMethod = paymentMethodRepository.findById(id);

        if (existingPaymentMethod.isPresent()) {
            paymentMethodRepository.delete(existingPaymentMethod.get());
            return ResponseEntity.ok("Método de pagamento deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}