package com.medistocks.authentication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.OrderDetailsModel;


public interface OrderDetailsRepository extends JpaRepository<OrderDetailsModel,Integer>{
    List<OrderDetailsModel> findByOrderId(int orderId);
}
