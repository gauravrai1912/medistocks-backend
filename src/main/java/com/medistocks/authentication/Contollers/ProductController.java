package com.medistocks.authentication.Contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.ProductModel;
import com.medistocks.authentication.Service.Impl.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproducts")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getproductbyname")
    public ResponseEntity<ProductModel> getProductByName(@RequestParam String name) {
        ProductModel product = productService.getProductByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody ProductModel product) {
        ProductModel newProduct = productService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateproduct")
    public ResponseEntity<ProductModel> updateProduct(@RequestParam String name, @RequestBody ProductModel product) {
        ProductModel updatedProduct = productService.updateProduct(name, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/deleteproduct")
    public String deleteProduct(@RequestParam String name) {
        productService.deleteProduct(name);
        return "Product Deleted" ;
    }

    @GetMapping("/checkproduct")
    public ResponseEntity<Boolean> checkProduct(@RequestParam String name) {
        boolean isPresent = productService.isProductPresent(name);
        return new ResponseEntity<>(isPresent, HttpStatus.OK);
    }
}
