package com.medistocks.authentication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medistocks.authentication.Entity.OrderModel;
import com.medistocks.authentication.Repository.OrderDetailsRepository;
import com.medistocks.authentication.Repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderModel saveOrder(OrderModel order) {
        return orderRepository.save(order);
    }

    public Optional<OrderModel> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public void deleteOrder(int id) {
        orderDetailsRepository.deleteByOrderId(id);
        orderRepository.deleteById(id);
    }

    public OrderModel updateOrder(int id, OrderModel order) {
        OrderModel existingOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setPharmacistId(order.getPharmacistId());
        existingOrder.setSupplierId(order.getSupplierId());

        return orderRepository.save(existingOrder);
    }
}
