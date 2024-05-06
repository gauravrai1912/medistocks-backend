package com.medistocks.authentication.Contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.SupplierModel;
import com.medistocks.authentication.Service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierModel>> getAllSuppliers() {
        List<SupplierModel> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierModel> getSupplierById(@PathVariable int id) {
        SupplierModel supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<SupplierModel> addSupplier(@RequestBody SupplierModel supplier) {
        SupplierModel newSupplier = supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(newSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierModel> updateSupplier(@PathVariable int id, @RequestBody SupplierModel supplier) {
        SupplierModel updatedSupplier = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return "Supplier details Deleted";
    }
}
