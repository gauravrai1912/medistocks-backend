package com.medistocks.authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.Otp;

public interface OtpRepository extends JpaRepository<Otp,Long> {

    Otp findByEmail(String email);
    Otp findByEmailAndUseCase(String email, String useCase);
    
}
