package com.medistocks.authentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.SupplierModel;



public interface SupplierRepository extends JpaRepository<SupplierModel,Integer> {

    Optional<SupplierModel> findBySupplierName(String name);

    void deleteBySupplierName(String name);

    boolean existsBySupplierName(String name);

    
} 
