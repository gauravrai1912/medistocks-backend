package com.medistocks.authentication.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.medistocks.authentication.Service.OtpService;

import lombok.Data;

@Component
@Data
public class OtpCleanupScheduler {
    
    @Autowired
    private OtpService otpService;

    @Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
    public void deleteUnusedOtps() {
        otpService.deleteUnusedOtps();
    }
}
