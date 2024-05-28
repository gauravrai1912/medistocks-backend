package com.medistocks.authentication.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.InventoryModel;

import com.medistocks.authentication.Repository.InventoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryModel> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public InventoryModel getInventoryById(String productName, String batchNo) {
        return inventoryRepository.findByProductNameAndBatchNumber(productName, batchNo).orElse(null);
        
    }
    public InventoryModel saveInventory(InventoryModel inventory) {
        return inventoryRepository.save(inventory);
    }

    public InventoryModel createInventory(InventoryModel inventory) {
        return inventoryRepository.save(inventory);
    }

    public InventoryModel updateInventory(String productName, String batchNo, InventoryModel updatedInventory) {
        InventoryModel existingInventory = inventoryRepository.findByProductNameAndBatchNumber(productName, batchNo).orElse(null);
        if (existingInventory != null) {
            existingInventory.setProductName(updatedInventory.getProductName());
            existingInventory.setSupplierName(updatedInventory.getSupplierName());
            existingInventory.setBatchNumber(updatedInventory.getBatchNumber());
            existingInventory.setPurchaseDate(updatedInventory.getPurchaseDate());
            existingInventory.setManufacturedDate(updatedInventory.getManufacturedDate());
            existingInventory.setPurchasePrice(updatedInventory.getPurchasePrice());
            existingInventory.setQuantity(updatedInventory.getQuantity());
            existingInventory.setExpirationDate(updatedInventory.getExpirationDate());
            return inventoryRepository.save(existingInventory);
        }
        return null;
    }
    

    public void deleteInventory(String productName, String batchNo) {
        inventoryRepository.deleteByProductNameAndBatchNumber(productName, batchNo);
    }

     public long getCountOfUniqueProductNames() {
        return inventoryRepository.countUniqueProductNames();
    }

    public long getCountOfProductsBelowQuantity() {
        return inventoryRepository.countProductsBelowQuantity();
    }

    public long getCountOfProductsExpiringWithinAWeek() {
        LocalDate oneWeekFromNow = LocalDate.now().plusWeeks(1);
        return inventoryRepository.countProductsExpiringWithinAWeek(oneWeekFromNow);
    }

}
