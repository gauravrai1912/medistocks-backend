package com.medistocks.authentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel,Integer>{

    Optional<ProductModel> findByProductName(String name);

    void deleteByProductName(String name);

}
