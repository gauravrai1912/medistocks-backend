package com.medistocks.authentication.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.DTO.UpdateOrderDetails;
import com.medistocks.authentication.Entity.OrderDetailsModel;
import com.medistocks.authentication.Repository.OrderDetailsRepository;

import java.util.List;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetailsModel> getAllOrderDetails(int orderId) {
        return (List<OrderDetailsModel>) orderDetailsRepository.findByOrderId(orderId);
    }

    public OrderDetailsModel saveOrderDetails(OrderDetailsModel orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetailsModel updateOrderDetails(OrderDetailsModel orderDetails) {
        OrderDetailsModel orderDetailsModel = orderDetailsRepository.findByOrderIdAndProductName(orderDetails.getOrderId(), orderDetails.getProductName());
        orderDetailsModel.setQuantityOrdered(orderDetails.getQuantityOrdered());
        orderDetailsModel.setTotalPrice(orderDetails.getTotalPrice());
        return orderDetailsRepository.save(orderDetailsModel);
    }

    public List<OrderDetailsModel> getOrderDetailsByOrderId(int orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }

    public OrderDetailsModel getDetails(int orderId, String productName) {
        return orderDetailsRepository.findByOrderIdAndProductName(orderId, productName);
    }
}
