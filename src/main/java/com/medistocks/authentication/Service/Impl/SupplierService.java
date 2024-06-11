package com.medistocks.authentication.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.SupplierModel;
import com.medistocks.authentication.Repository.SupplierRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierModel> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<SupplierModel> getSupplierByName(String name) {
        return supplierRepository.findBySupplierName(name);
    }

    public SupplierModel saveSupplier(SupplierModel supplier) {
        return supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplier(String name) {
        supplierRepository.deleteBySupplierName(name);
    }

    public SupplierModel updateSupplier(String name, SupplierModel supplier) {
        SupplierModel existingSupplier = supplierRepository.findBySupplierName(name)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        existingSupplier.setSupplierName(supplier.getSupplierName());
        existingSupplier.setContactNumber(supplier.getContactNumber());
        existingSupplier.setEmail(supplier.getEmail());
        existingSupplier.setAddress(supplier.getAddress());
        
        return supplierRepository.save(existingSupplier);
    }

    public boolean isSupplierPresent(String name) {
        return supplierRepository.existsBySupplierName(name);
    }
}
