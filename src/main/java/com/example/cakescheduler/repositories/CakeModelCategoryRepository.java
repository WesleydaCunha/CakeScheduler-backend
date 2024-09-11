package com.example.cakescheduler.repositories;


import com.example.cakescheduler.domain.cake.CakeModelCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CakeModelCategoryRepository extends JpaRepository<CakeModelCategory, Long> {

}
