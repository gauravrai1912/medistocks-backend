package com.medistocks.authentication.Repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.medistocks.authentication.Entity.OrderDetailsModel;




public interface OrderDetailsRepository extends CrudRepository<OrderDetailsModel, Integer>{
    List<OrderDetailsModel> findByOrderId(int orderId);

    void deleteByOrderId(int id);

    OrderDetailsModel findByOrderIdAndProductName(int orderId, String productName);


    
}
