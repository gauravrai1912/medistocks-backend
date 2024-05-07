package com.medistocks.authentication.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.ProductModel;
import com.medistocks.authentication.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> getProductById(int id) {
        return productRepository.findById(id);
    }

    public ProductModel saveProduct(ProductModel product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public ProductModel updateProduct(int id, ProductModel product) {
        ProductModel existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setSupplierId(product.getSupplierId());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setReorderLevel(product.getReorderLevel());
        
        return productRepository.save(existingProduct);
    }
}
