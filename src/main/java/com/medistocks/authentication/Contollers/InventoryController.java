package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.InventoryModel;
import com.medistocks.authentication.Service.Impl.InventoryNotificationService;
import com.medistocks.authentication.Service.Impl.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryNotificationService inventoryNotificationService;

    @GetMapping
    public ResponseEntity<List<InventoryModel>> getAllInventory() {
        List<InventoryModel> inventory = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryModel> getInventoryById(@PathVariable int id) {
        InventoryModel inventory = inventoryService.getInventoryById(id);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryModel> addInventory(@RequestBody InventoryModel inventory) {
        InventoryModel newInventory = inventoryService.saveInventory(inventory);
        return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryModel> updateInventory(@PathVariable int id, @RequestBody InventoryModel inventory) {
        InventoryModel updatedInventory = inventoryService.updateInventory(id, inventory);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteInventory(@PathVariable int id) {
        inventoryService.deleteInventory(id);
        return "Product deleted from inventory";
    }

    

    @GetMapping("/check")
    public ResponseEntity<String> checkInventoryAndSendNotifications() {
        inventoryNotificationService.checkInventoryAndSendNotifications();
        return ResponseEntity.status(HttpStatus.OK).body("Inventory check initiated.");
    }

    @GetMapping("/check2")
    public ResponseEntity<String> checkExpiryAndSendNotifications() {
        inventoryNotificationService.checkExpirydateAndSendNotifications();;
        return ResponseEntity.status(HttpStatus.OK).body("Expiry check initiated.");
    }
}
