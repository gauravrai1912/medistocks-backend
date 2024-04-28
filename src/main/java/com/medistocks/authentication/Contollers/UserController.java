package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.ForgotPasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
import com.medistocks.authentication.DTO.ResetPasswordRequest;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.Service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Response> signup(@RequestBody Request request) {
        return userService.signUp(request);
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

    @PostMapping("forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Response response = userService.forgotPassword(request.getEmail());
        HttpStatus status = response.getStatusCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("resetPassword")
    public ResponseEntity<Response> resetPasswordWithOTP(@RequestBody ResetPasswordRequest request) {
        Response response = userService.resetPasswordWithOTP(request.getEmail(), request.getOtp(), request.getNewPassword());
        HttpStatus status;
        if (response.getStatusCode() == 200) {
            status = HttpStatus.OK;
        } else if (response.getStatusCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(response);
    }

}
