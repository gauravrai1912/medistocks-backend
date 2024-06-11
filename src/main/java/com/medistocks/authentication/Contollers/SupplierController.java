package com.medistocks.authentication.Contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.SupplierModel;
import com.medistocks.authentication.Service.Impl.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin("*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/getallsuppliers")
    public ResponseEntity<List<SupplierModel>> getAllSuppliers() {
        List<SupplierModel> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/getsupplierbyname")
    public ResponseEntity<SupplierModel> getSupplierByName(@RequestParam String name) {
        SupplierModel supplier = supplierService.getSupplierByName(name)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<SupplierModel> addSupplier(@RequestBody SupplierModel supplier) {
        SupplierModel newSupplier = supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(newSupplier);
    }

    @PutMapping("/updatesupplier")
    public ResponseEntity<SupplierModel> updateSupplier(@RequestParam String name, @RequestBody SupplierModel supplier) {
        SupplierModel updatedSupplier = supplierService.updateSupplier(name, supplier);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/deletesupplier")
    public String deleteSupplier(@RequestParam String name) {
        supplierService.deleteSupplier(name);
        return "Supplier details Deleted";
    }

    @GetMapping("/checksupplier")
    public ResponseEntity<Boolean> checkSupplier(@RequestParam String name) {
        boolean isPresent = supplierService.isSupplierPresent(name);
        return ResponseEntity.ok(isPresent);
    }
}
