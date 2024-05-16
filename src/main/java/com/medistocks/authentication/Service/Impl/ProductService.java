package com.medistocks.authentication.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.ProductModel;
import com.medistocks.authentication.Repository.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> getProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    public ProductModel saveProduct(ProductModel product) {
        return productRepository.save(product);
    }
    @Transactional
    public void deleteProduct(String name) {
        productRepository.deleteByProductName(name);
    }

    public ProductModel updateProduct(String name, ProductModel product) {
        ProductModel existingProduct = productRepository.findByProductName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setSupplierName(product.getSupplierName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setReorderLevel(product.getReorderLevel());
        
        return productRepository.save(existingProduct);
    }
}
