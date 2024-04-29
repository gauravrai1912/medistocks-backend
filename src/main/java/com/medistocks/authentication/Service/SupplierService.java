package com.medistocks.authentication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.SupplierModel;
import com.medistocks.authentication.Repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierModel> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<SupplierModel> getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    public SupplierModel saveSupplier(SupplierModel supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(int id) {
        supplierRepository.deleteById(id);
    }

    public SupplierModel updateSupplier(int id, SupplierModel supplier) {
        SupplierModel existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        existingSupplier.setSupplierName(supplier.getSupplierName());
        existingSupplier.setContactNumber(supplier.getContactNumber());
        existingSupplier.setEmail(supplier.getEmail());
        existingSupplier.setAddress(supplier.getAddress());
        
        return supplierRepository.save(existingSupplier);
    }
}
