package com.medistocks.authentication.Contollers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import com.medistocks.authentication.DTO.OtpRequest;
import com.medistocks.authentication.DTO.OtpValidationRequest;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.Service.Impl.OtpService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/otp")
@AllArgsConstructor
public class OtpController {

    public final OtpService otpService;

    // @PostMapping("sendOtp")
    // public Response sendOtp(@RequestBody OtpRequest otpRequest, @RequestParam String useCase) {
    //     return otpService.sendOtp(otpRequest, useCase);
    // }

    // @PostMapping("validateOtp")
    // public Response validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
    //     return otpService.validateOtp(otpValidationRequest);
    // }

    
}
