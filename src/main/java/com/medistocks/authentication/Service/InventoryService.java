package com.medistocks.authentication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.InventoryModel;
import com.medistocks.authentication.Repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryModel> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public InventoryModel getInventoryById(int inventoryId) {
        return inventoryRepository.findById(inventoryId).orElse(null);
    }

    public InventoryModel createInventory(InventoryModel inventory) {
        return inventoryRepository.save(inventory);
    }

    public InventoryModel updateInventory(int inventoryId, InventoryModel updatedInventory) {
        InventoryModel existingInventory = inventoryRepository.findById(inventoryId).orElse(null);
        if (existingInventory != null) {
            existingInventory.setProductId(updatedInventory.getProductId());
            existingInventory.setSupplierId(updatedInventory.getSupplierId());
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

    public void deleteInventory(int inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }
}
