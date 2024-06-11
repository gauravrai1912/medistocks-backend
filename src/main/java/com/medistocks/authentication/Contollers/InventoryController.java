package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.InventoryModel;
import com.medistocks.authentication.Service.Impl.InventoryNotificationService;
import com.medistocks.authentication.Service.Impl.InventoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryNotificationService inventoryNotificationService;

    @GetMapping("/getall")
    public ResponseEntity<List<InventoryModel>> getAllInventory() {
        List<InventoryModel> inventory = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<InventoryModel> getInventoryById(@RequestParam String productName, @RequestParam String batchNo) {
        InventoryModel inventory = inventoryService.getInventoryById(productName, batchNo);
        
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryModel> addInventory(@RequestBody InventoryModel inventory) {
        InventoryModel newInventory = inventoryService.saveInventory(inventory);
        return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InventoryModel> updateInventory(@RequestParam String productName, @RequestParam String batchNo, @RequestBody InventoryModel inventory) {
        InventoryModel updatedInventory = inventoryService.updateInventory(productName,batchNo, inventory);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @DeleteMapping
    public String deleteInventory(@RequestParam String productName , @RequestParam String batchNo) {
        inventoryService.deleteInventory(productName,batchNo);
        return "Product deleted from inventory";
    }


    @GetMapping("/summary")
    public Map<String, Long> getInventorySummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalUniqueProducts", inventoryService.getCountOfUniqueProductNames());
        summary.put("productsBelowQuantity", inventoryService.getCountOfProductsBelowQuantity());
        summary.put("productsExpiringWithinAWeek", inventoryService.getCountOfProductsExpiringWithinAWeek());
        return summary;
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
