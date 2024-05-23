package com.medistocks.authentication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class OrderModel {
    
    @Id
    private int orderId;
    private LocalDate orderDate;
    private int pharmacistId;
    private String supplierName;
  
}
