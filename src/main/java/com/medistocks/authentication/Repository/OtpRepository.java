package com.medistocks.authentication.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.Otp;

import jakarta.transaction.Transactional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    
    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime time);

    Optional<Otp> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
