package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.OrderDetailsModel;
import com.medistocks.authentication.Service.OrderDetailsService;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public List<OrderDetailsModel> getAllOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }

    @PostMapping
    public OrderDetailsModel saveOrderDetails(@RequestBody OrderDetailsModel orderDetails) {
        return orderDetailsService.saveOrderDetails(orderDetails);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetailsModel> getOrderDetailsByOrderId(@PathVariable int orderId) {
        return orderDetailsService.getOrderDetailsByOrderId(orderId);
    }
}
