package com.medistocks.authentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.InventoryModel;

import jakarta.transaction.Transactional;



public interface InventoryRepository extends JpaRepository<InventoryModel,Integer> {

    Optional<InventoryModel> findByProductName(String productName);

    Optional<InventoryModel> findByProductNameAndBatchNumber(String productName, String batchNumber);

    @Transactional
    void deleteByProductNameAndBatchNumber(String productName, String batchNo);

}
