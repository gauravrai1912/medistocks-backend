package com.medistocks.authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel,Integer>{

}
