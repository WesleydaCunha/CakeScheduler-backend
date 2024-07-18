package com.example.loginauthapi.service;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.domain.cake.Filling;
import com.example.loginauthapi.domain.cake.PaymentMethod;
import com.example.loginauthapi.repositories.CakeModelRepository;
import com.example.loginauthapi.repositories.ComplementRepository;
import com.example.loginauthapi.repositories.FillingRepository;
import com.example.loginauthapi.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CakeService {

    private final CakeModelRepository cakeModelRepository;
    private final FillingRepository fillingRepository;
    private final ComplementRepository complementRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public CakeService(CakeModelRepository cakeModelRepository, FillingRepository fillingRepository, ComplementRepository complementRepository, PaymentMethodRepository paymentMethodRepository) {
        this.cakeModelRepository = cakeModelRepository;
        this.fillingRepository = fillingRepository;
        this.complementRepository = complementRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<CakeModel> findAllCakeModels() {
        return cakeModelRepository.findAll();
    }

    public CakeModel findCakeModelById(Long id) {
        return cakeModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cake model not found"));
    }

    public CakeModel saveCakeModel(CakeModel cakeModel) {
        return cakeModelRepository.save(cakeModel);
    }

    public void deleteCakeModel(Long id) {
        cakeModelRepository.deleteById(id);
    }

    public List<Filling> findAllFillings() {
        return fillingRepository.findAll();
    }

    public Filling findFillingById(Long id) {
        return fillingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filling not found"));
    }

    public Filling saveFilling(Filling filling) {
        return fillingRepository.save(filling);
    }

    public void deleteFilling(Long id) {
        fillingRepository.deleteById(id);
    }

    public List<Complement> findAllComplements() {
        return complementRepository.findAll();
    }

    public Complement findComplementById(Long id) {
        return complementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complement not found"));
    }

    public Complement saveComplement(Complement complement) {
        return complementRepository.save(complement);
    }

    public void deleteComplement(Long id) {
        complementRepository.deleteById(id);
    }

    public List<PaymentMethod> findAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod findPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
