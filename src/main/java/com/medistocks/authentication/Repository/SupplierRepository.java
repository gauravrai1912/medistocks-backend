package com.medistocks.authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.SupplierModel;



public interface SupplierRepository extends JpaRepository<SupplierModel,Integer> {

    
} 
