package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.cake.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeModelRepository extends JpaRepository<CakeModel, Long> {
    @Query("SELECT cm FROM CakeModel cm WHERE cm.category.category_name = :categoryName")
    List<CakeModel> findByCategoryCategoryName(@Param("categoryName") String categoryName);
}
