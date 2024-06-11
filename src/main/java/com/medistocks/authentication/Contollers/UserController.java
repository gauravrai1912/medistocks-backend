package com.medistocks.authentication.Contollers;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.ForgotPasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.OtpValidationRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.ResetPasswordRequest;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.DTO.UpdateUser;
import com.medistocks.authentication.Entity.Otp;
import com.medistocks.authentication.Repository.OtpRepository;
import com.medistocks.authentication.Service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://52.66.201.221:3000")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final OtpRepository otpRepository;

    @PostMapping("signup")
    public ResponseEntity<Response> signup(@RequestBody Request request) {
        return userService.signUp(request);
    }

    @GetMapping("/getuserinfo")
    public ResponseEntity<UpdateUser> getUserInfo(@RequestHeader String email, String token) {
        // Get the user information
        UpdateUser updateUser = userService.getUserInfo(email, token);

        // Return the user information in a ResponseEntity
        return ResponseEntity.ok(updateUser);
    }

     @PutMapping("/updateProfile")
    public ResponseEntity<String> updateUserProfile(@RequestParam String email, @RequestBody UpdateUser updateUserRequest) {
        try {
            // Assuming UserService has a method to update user profile
            userService.updateUserProfile(email, updateUserRequest);
            return new ResponseEntity<>("User profile updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("updateUser")
    public ResponseEntity<Response> updateUser(@RequestHeader String email, String token,
            @RequestBody UpdateUser updateUser) {
        return userService.updateUser(email, token, updateUser);
    }

    @PostMapping("login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("changePassword")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request) {
        Response response = userService.changePassword(request);
        HttpStatus status = response.getStatusCode() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("validateOtp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidationRequest request) {
        Optional<Otp> otpOptional = otpRepository.findByEmail(request.getEmail());
        System.out.println(request.getEmail());
        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            if (otp.getOtp().equals(request.getOtp())) {
                if (otp.getCreatedAt().plusMinutes(5).isAfter(LocalDateTime.now())) { // Assuming OTP expires in 5 minutes
                    return ResponseEntity.ok("OTP validation successful");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP has expired");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No OTP found for the provided email");
        }
    }

    @PostMapping("forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Response response = userService.forgotPassword(request.getEmail());
        HttpStatus status = response.getStatusCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    // @PostMapping("resetPassword")
    // public ResponseEntity<Response> resetPasswordWithOtp(@RequestBody ResetPasswordRequest request) {
    //     Response response = userService.resetPasswordWithOtp(request.getEmail(), request.getOtp(),
    //             request.getNewPassword());
    //     HttpStatus status;
    //     if (response.getStatusCode() == 200) {
    //         status = HttpStatus.OK;
    //     } else if (response.getStatusCode() == 404) {
    //         status = HttpStatus.NOT_FOUND;
    //     } else {
    //         status = HttpStatus.BAD_REQUEST;
    //     }
    //     return ResponseEntity.status(status).body(response);
    // }


    @GetMapping("getUser")
    public ResponseEntity<Response> getUserById(@RequestHeader UUID userId) {
        try {
            ResponseEntity<Response> response = userService.getUserById(userId);
            return response;
        } catch (NoSuchElementException e) {
            // If the user is not found, return a custom "User not found" response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .responseMessage("User not found")
                            .build());
        }
    }

    @PutMapping("resetPassword")
    public ResponseEntity<String> updatePasswordByEmail( @RequestBody ResetPasswordRequest request) {
        String email = request.getEmail();
        String newPassword = request.getNewPassword();
        
        userService.updatePasswordByEmail(email, newPassword);
        return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK);
    }

}
