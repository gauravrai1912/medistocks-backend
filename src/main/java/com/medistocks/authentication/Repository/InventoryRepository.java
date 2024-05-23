package com.medistocks.authentication.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medistocks.authentication.Entity.InventoryModel;

import jakarta.transaction.Transactional;



public interface InventoryRepository extends JpaRepository<InventoryModel,Integer> {

    Optional<InventoryModel> findByProductName(String productName);

    Optional<InventoryModel> findByProductNameAndBatchNumber(String productName, String batchNumber);

    @Transactional
    void deleteByProductNameAndBatchNumber(String productName, String batchNo);

     @Query("SELECT COUNT(DISTINCT i.productName) FROM InventoryModel i")
    long countUniqueProductNames();

    @Query("SELECT COUNT(i) FROM InventoryModel i WHERE i.quantity < 100")
    long countProductsBelowQuantity();

    @Query("SELECT COUNT(i) FROM InventoryModel i WHERE i.expirationDate <= :oneWeekFromNow")
    long countProductsExpiringWithinAWeek(LocalDate oneWeekFromNow);

}
