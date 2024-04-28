package com.medistocks.authentication.Service;

import org.springframework.http.ResponseEntity;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.Response;

public interface UserService {
    
    ResponseEntity<Response> signUp(Request request);
    ResponseEntity<Response> login(LoginRequest request);
    Response sendOtp();
    Response validateOtp();
    public Response resetPasswordWithOTP(String email, String otp, String newPassword);
    Response forgotPassword(String email);
    Response changePassword(ChangePasswordRequest request);
}
