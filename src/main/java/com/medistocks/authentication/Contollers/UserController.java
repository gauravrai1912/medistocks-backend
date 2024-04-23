package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medistocks.authentication.DTO.ChangePasswordRequest;
import com.medistocks.authentication.DTO.LoginRequest;
import com.medistocks.authentication.DTO.Request;
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

}
