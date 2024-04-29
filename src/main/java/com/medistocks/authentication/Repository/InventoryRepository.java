package com.medistocks.authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.InventoryModel;



public interface InventoryRepository extends JpaRepository<InventoryModel,Integer> {

}
