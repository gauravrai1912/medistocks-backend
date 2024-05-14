package com.medistocks.authentication.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.OrderDetailsModel;
import com.medistocks.authentication.Repository.OrderDetailsRepository;

import java.util.List;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetailsModel> getAllOrderDetails() {
        return (List<OrderDetailsModel>) orderDetailsRepository.findAll();
    }

    public OrderDetailsModel saveOrderDetails(OrderDetailsModel orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public List<OrderDetailsModel> getOrderDetailsByOrderId(int orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }
}
