package com.medistocks.authentication.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.DTO.EmailDetails;
import com.medistocks.authentication.Entity.InventoryModel;
import com.medistocks.authentication.Entity.ProductModel;
import com.medistocks.authentication.Repository.InventoryRepository;
import com.medistocks.authentication.Repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryNotificationService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 86400000) // Runs every day, adjust as needed
    public void checkInventoryAndSendNotifications() {
        List<InventoryModel> inventories = inventoryRepository.findAll();
        for (InventoryModel inventory : inventories) {
            ProductModel product = productRepository.findByProductName(inventory.getProductName()).orElse(null);
            if (product != null && inventory.getQuantity() < product.getReorderLevel()) {
                sendNotification(product, inventory);
            }
        }
    }
    
    @Scheduled(fixedRate = 86400000) // Runs every day, adjust as needed
    public void checkExpirydateAndSendNotifications() {
        List<InventoryModel> inventories = inventoryRepository.findAll();
        for (InventoryModel inventory : inventories) {
            if (isExpiringWithinWeek(inventory.getExpirationDate())) {
                sendExpiryNotification(inventory);
            }
        }
    }

    private boolean isExpiringWithinWeek(LocalDate expiryDate) {
        LocalDate today = LocalDate.now();
        LocalDate oneWeekLater = today.plusWeeks(1);
        boolean s = !expiryDate.isAfter(oneWeekLater) && expiryDate.isAfter(today);
        System.out.println(s);
        return s;
    }
    

    private void sendExpiryNotification(InventoryModel inventory) {
        ProductModel product = productRepository.findByProductName(inventory.getProductName()).orElse(null);
        System.out.println(product);
        if (product != null) {
            String message = "Product " + product.getProductName() + " is expiring soon. Expiry Date: " + inventory.getExpirationDate();
            String subject = "Expiry Notification: " + product.getProductName();
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient("gauravrai1912@gmail.com"); // Change recipient email address
            emailDetails.setSubject(subject);
            emailDetails.setMessageBody(message);
            emailService.sendEmail(emailDetails);
        }
    }
     

    private void sendNotification(ProductModel product, InventoryModel inventory) {
        String message = "Product " + product.getProductName() + " has quantity below reorder level. Current Quantity: " + inventory.getQuantity();
        String subject = "Reorder Notification: " + product.getProductName();
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("gauravrai1912@gmail.com"); // Change recipient email address
        emailDetails.setSubject(subject);
        emailDetails.setMessageBody(message);
        emailService.sendEmail(emailDetails);
    }
}

