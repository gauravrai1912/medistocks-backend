package com.medistocks.authentication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "inventory")
public class InventoryModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;
    private int productId;
    private int supplierId;
    private String batchNumber;
    private Date purchaseDate;
    private Date manufacturedDate;
    private BigDecimal purchasePrice;
    private int quantity;
    private Date expirationDate;
  
}
