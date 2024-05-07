package com.medistocks.authentication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.DTO.UserInfo;
import com.medistocks.authentication.Entity.User;

@Component
public interface UserService {
    
    ResponseEntity<Response> signUp(Request request);
    ResponseEntity<Response> login(LoginRequest request);
    Response resetPasswordWithOTP(String email, String otp, String newPassword);
    Response forgotPassword(String email);
    Response changePassword(ChangePasswordRequest request);
    public List<User> getAllUsers();
    ResponseEntity<Response> getUserById(UUID userId);
    public void deleteUser(UUID userId);
    ResponseEntity<Response> updateUser(String userEmail,String token, UserInfo userInfo);
}
