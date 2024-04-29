package com.medistocks.authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.OrderModel;



public interface OrderRepository extends JpaRepository<OrderModel,Integer>{

}
