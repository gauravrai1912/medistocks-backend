package com.medistocks.authentication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "inventory")
public class InventoryModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;
    private String productName; 
    private String supplierName;
    private String batchNumber;
    private LocalDate purchaseDate;
    private LocalDate manufacturedDate;
    private BigDecimal purchasePrice;
    private int quantity;
    private LocalDate expirationDate;
  
}
