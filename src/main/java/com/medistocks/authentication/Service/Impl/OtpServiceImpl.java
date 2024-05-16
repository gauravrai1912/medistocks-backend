 package com.medistocks.authentication.Service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.Otp;
import com.medistocks.authentication.Repository.OtpRepository;
import com.medistocks.authentication.Service.OtpService;

import lombok.Data;

@Service
@Data
public class OtpServiceImpl implements OtpService {
    
    @Autowired
    private OtpRepository otpRepository;

   
    @Override
    public void saveOrUpdateOtp(String email, String otp) {
        Optional<Otp> optionalOtp = otpRepository.findByEmail(email);
        Otp newOtp = optionalOtp.orElse(new Otp());
        newOtp.setEmail(email);
        newOtp.setOtp(otp);
        newOtp.setCreatedAt(LocalDateTime.now());
        otpRepository.save(newOtp);
    }

    @Override
    public Optional<Otp> findOtpByEmail(String email) {
        return otpRepository.findByEmail(email);
    }

    @Override
    public void deleteOtp(String email) {
        otpRepository.deleteByEmail(email);
    }

    @Scheduled(fixedRate = 60000) // Scheduled to run every 3 minutes (180,000 milliseconds)
    public void deleteUnusedOtps() {
        LocalDateTime threeMinutesAgo = LocalDateTime.now().minusMinutes(5);
        otpRepository.deleteByCreatedAtBefore(threeMinutesAgo);
    }
}
