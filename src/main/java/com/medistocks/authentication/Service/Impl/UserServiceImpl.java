package com.medistocks.authentication.Service.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.EmailDetails;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.DTO.UserInfo;
import com.medistocks.authentication.Entity.User;
import com.medistocks.authentication.Repository.UserRepository;
import com.medistocks.authentication.Service.UserService;
import com.medistocks.authentication.Utils.AppUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    private Map<String, String> otpStorage = new HashMap<>();

    @Override
    public ResponseEntity<Response> signUp(Request request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
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
                .pharmacyName(request.getPharmacyName())
                .phoneNumber(request.getPhoneNumber())
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
                    .responseMessage("user not found")
                    .build());
        }
    }

    @Override
    public Response forgotPassword(String email) {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Response.builder()
                    .statusCode(404)
                    .responseMessage("User not found")
                    .build();
        }

        // Generate OTP
        String otp = AppUtils.generateOtp();

        // Store the OTP for verification
        otpStorage.put(email, otp);

        // Prepare email details
        String subject = "Your OTP for password reset is: " + otp;
        String messageBody = "Password Reset OTP";
        EmailDetails emailDetails = new EmailDetails(email, subject, messageBody);

        // Send OTP to the user
        emailService.sendEmail(emailDetails);

        return Response.builder()
                .statusCode(200)
                .responseMessage("OTP sent successfully")
                .build();
    }

    public Response resetPasswordWithOTP(String email, String otp, String newPassword) {
        // Check if the OTP matches
        String storedOTP = otpStorage.get(email);
        if (storedOTP == null || !storedOTP.equals(otp)) {
            return Response.builder()
                    .statusCode(400)
                    .responseMessage("Invalid OTP")
                    .build();
        }

        // Update the password
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            // Clear the OTP from storage
            otpStorage.remove(email);
            return Response.builder()
                    .statusCode(200)
                    .responseMessage("Password changed successfully")
                    .build();
        } else {
            return Response.builder()
                    .statusCode(404)
                    .responseMessage("User not found")
                    .build();
        }
    }

    @Override
    public Response changePassword(ChangePasswordRequest request) {
        String email = request.getEmail();
        String oldPassword = request.getPassword();
        String newPassword = request.getNewPassword();

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Response.builder()
                    .statusCode(404)
                    .responseMessage("User not found")
                    .build();
        }

        // Validate if the old password matches
        User user = optionalUser.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return Response.builder()
                    .statusCode(400)
                    .responseMessage("Invalid old password")
                    .build();
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return Response.builder()
                .statusCode(200)
                .responseMessage("Password changed successfully")
                .build();
    }

}
