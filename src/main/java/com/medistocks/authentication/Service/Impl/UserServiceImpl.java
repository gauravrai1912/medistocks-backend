package com.medistocks.authentication.Service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.DTO.UserInfo;
import com.medistocks.authentication.Entity.User;
import com.medistocks.authentication.Repository.UserRepository;
import com.medistocks.authentication.Service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenService tokenService;
    
    @Override
    public ResponseEntity<Response> signUp(Request request) {
        
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Response.builder()
            .statusCode(400)
            .responseMessage("duplicate user")
            .build());
        }
        User user = User.builder()
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .firstName(request.getFirstName())
              .lastName(request.getLastName())
              .build();
        User saveUser = userRepository.save(user);

        return ResponseEntity.ok(Response.builder()
                              .statusCode(200)
                              .responseMessage("Success")
                              .userInfo(modelMapper.map(saveUser, UserInfo.class))
                              .build());
    }

    
    @Override
    public ResponseEntity<Response> login(LoginRequest request) {
        // Retrieve the user from the database using the provided email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            // User found, check if the provided password matches the user's password
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // Password matches, authentication successful
                // Generate and include token
                String token = tokenService.generateToken(user);
                // You can include additional logic here if needed
                return ResponseEntity.ok(Response.builder()
                        .statusCode(200)
                        .responseMessage("Login successful")
                        .token(token)
                        .build());
            } else {
                // Password does not match, authentication failed
                return ResponseEntity.badRequest().body(Response.builder()
                        .statusCode(401)
                        .responseMessage("Invalid email or password")
                        .build());
            }
        } else {
            // User not found, authentication failed
            return ResponseEntity.badRequest().body(Response.builder()
                    .statusCode(401)
                    .responseMessage("Invalid email or password")
                    .build());
        }
    }

    @Override
    public Response sendOtp() {
        return null;
    }

    @Override
    public Response validateOtp() {
        return null;
    }

    @Override
    public Response resetPassword() {
        return null;
    }

    @Override
    public Response changePassword() {
        return null;
    }
    
}
