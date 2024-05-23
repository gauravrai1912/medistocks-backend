package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.DTO.UpdateOrderDetails;
import com.medistocks.authentication.Entity.OrderDetailsModel;
import com.medistocks.authentication.Service.Impl.OrderDetailsService;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/{orderId}")
    public List<OrderDetailsModel> getAllOrderDetails(@PathVariable int orderId) {
        return orderDetailsService.getAllOrderDetails(orderId);
    }

    @GetMapping("/getDetails")
    public OrderDetailsModel getAllOrderDetails(@RequestParam int orderId, @RequestParam String productName) {
        return orderDetailsService.getDetails(orderId,productName);
    }
        

    // @PutMapping
    // public OrderDetailsModel updateOrderDetails(@RequestBody OrderDetailsModel orderDetails) {
    //     return orderDetailsService.saveOrderDetails(orderDetails);
    // }
   

    @PutMapping
    public OrderDetailsModel saveOrderDetails(@RequestBody UpdateOrderDetails orderDetails) {
        return orderDetailsService.updatOrderDetails(orderDetails);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetailsModel> getOrderDetailsByOrderId(@PathVariable int orderId) {
        return orderDetailsService.getOrderDetailsByOrderId(orderId);
    }
}
