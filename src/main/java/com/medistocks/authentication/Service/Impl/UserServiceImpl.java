package com.medistocks.authentication.Service.Impl;

import java.util.Optional;
import java.util.UUID;

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
import com.medistocks.authentication.DTO.UpdateUser;
import com.medistocks.authentication.DTO.UserInfo;
import com.medistocks.authentication.Entity.Otp;
import com.medistocks.authentication.Entity.User;
import com.medistocks.authentication.Repository.UserRepository;
import com.medistocks.authentication.Service.OtpService;
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

    @Autowired
    private OtpService otpService;

    // private Map<String, String> otpStorage = new HashMap<>();

    public ResponseEntity<Response> signUp(Request request) {

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent()) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .statusCode(400)
                    .responseMessage("User with this email already exists!")
                    .build());
        }

        Optional<User> existingUserByEmployeeId = userRepository.findByEmployeeId(request.getEmployeeId());
        if (existingUserByEmployeeId.isPresent()) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .statusCode(400)
                    .responseMessage("User with this employee ID already exists! Please choose a different one.")
                    .build());
        }

        User user = User.builder()
                .employeeId(request.getEmployeeId())
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

    public ResponseEntity<Response> updateUser(String userEmail, String token, UpdateUser updateUser) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            user.setPhoneNumber(updateUser.getPhoneNumber());

            // Save the updated user
            User savedUser = userRepository.save(user);

            // Return success response with updated user info
            return ResponseEntity.ok(Response.builder()
                    .statusCode(200)
                    .responseMessage("User information updated successfully")
                    .userInfo(modelMapper.map(savedUser, UserInfo.class))
                    .build());
        } else {
            // Return error response if user not found
            return ResponseEntity.notFound().build();
        }
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
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Response.builder()
                    .statusCode(404)
                    .responseMessage("User not found")
                    .build();
        }

        String otp = AppUtils.generateOtp();
        otpService.saveOrUpdateOtp(email, otp);

        String subject = "Your OTP for password reset is: " + otp;
        String messageBody = "Password Reset OTP";
        EmailDetails emailDetails = new EmailDetails(email, subject, messageBody);

        emailService.sendEmail(emailDetails);

        return Response.builder()
                .statusCode(200)
                .responseMessage("OTP sent successfully")
                .build();
    }

    @Override
    public Response resetPasswordWithOtp(String email, String otp, String newPassword) {
        Optional<Otp> optionalOtp = otpService.findOtpByEmail(email);
        if (optionalOtp.isEmpty() || !optionalOtp.get().getOtp().equals(otp)) {
            return Response.builder()
                    .statusCode(400)
                    .responseMessage("Invalid OTP")
                    .build();
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            otpService.deleteOtp(email);
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

    public ResponseEntity<Response> getUserById(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            // Map the user to UserInfo
            UserInfo userInfo = modelMapper.map(optionalUser.get(), UserInfo.class);
            // Return success response with user info
            return ResponseEntity.ok(Response.builder()
                    .statusCode(200)
                    .responseMessage("User found")
                    .userInfo(userInfo)
                    .build());
        } else {
            // Return error response if user not found
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(UUID userId, UserInfo user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPharmacyName(user.getPharmacyName());

        return userRepository.save(existingUser);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            // Handle user not found scenario
            throw new RuntimeException("User with email " + email + " not found.");
        }
    }

    public UpdateUser getUserInfo(String email, String token) {
        // Fetch the existing user from the repository
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map the relevant fields to the UpdateUser object
        UpdateUser updateUser = new UpdateUser();
        updateUser.setPhoneNumber(existingUser.getPhoneNumber());
        updateUser.setFirstName(existingUser.getFirstName());
        updateUser.setLastName(existingUser.getLastName());

        return updateUser;
    }

    public void updateUserProfile(String email, UpdateUser updateUserRequest) {
        // Retrieve the user entity from the database based on the email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;
        
        // Update the user entity with the new data
        if (user != null) {
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            
            // Save the updated user entity back to the database
            userRepository.save(user);
        } 
    }
}
