package com.medistocks.authentication.Contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.OrderModel;
import com.medistocks.authentication.Service.Impl.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderModel getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @PostMapping
    public OrderModel saveOrder(@RequestBody OrderModel order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public OrderModel updateOrder(@PathVariable int id, @RequestBody OrderModel order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }
}
