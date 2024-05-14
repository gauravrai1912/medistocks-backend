package com.medistocks.authentication.Service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.medistocks.authentication.Entity.Otp;

@Component
public interface OtpService {
    void saveOrUpdateOtp(String email, String otp);

    Optional<Otp> findOtpByEmail(String email);

    void deleteOtp(String email);

    void deleteUnusedOtps();
}
