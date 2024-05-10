package com.medistocks.authentication.Service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.DTO.UserInfo;

@Component
public interface UserService {
    
    ResponseEntity<Response> signUp(Request request);
    ResponseEntity<Response> login(LoginRequest request);
    Response changePassword(ChangePasswordRequest request);
    ResponseEntity<Response> getUserById(UUID userId);
    void deleteUser(UUID userId);
    ResponseEntity<Response> updateUser(String userEmail,String token, UserInfo userInfo);
    Response resetPasswordWithOtp(String email, String otp, String newPassword);
    Response forgotPassword(String email);
}
