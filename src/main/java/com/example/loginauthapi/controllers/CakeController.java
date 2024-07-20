package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.cake.CakeModel;
import com.example.loginauthapi.domain.cake.Complement;
import com.example.loginauthapi.dto.CakeModelRequestDTO;
import com.example.loginauthapi.dto.ComplementRequestDTO;
import com.example.loginauthapi.repositories.CakeModelRepository;
import com.example.loginauthapi.repositories.ComplementRepository;
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


    @GetMapping("/models")
    public ResponseEntity<List<CakeModel>> getAllCakeModels() {
        List<CakeModel> listModels = cakeModelRepository.findAll();
        return ResponseEntity.ok(listModels);
    }

    @PostMapping("/models/register")
    public ResponseEntity<String> saveCakeModel(@RequestBody CakeModelRequestDTO body) {
        CakeModel newCakeModel = new CakeModel();
        newCakeModel.setName(body.name());
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
            cakeModel.setName(body.name());
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
        newComplement.setName(body.name());
        newComplement.setprice(body.price());
        this.complementRepository.save(newComplement);
        return ResponseEntity.ok("Sucesso!");
    }



}