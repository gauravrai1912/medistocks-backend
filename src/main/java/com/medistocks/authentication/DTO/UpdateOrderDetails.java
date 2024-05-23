package com.medistocks.authentication.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateOrderDetails {
    private int orderId;
    private String productName;
    private int quantityOrdered;
    private BigDecimal totalPrice;
}
