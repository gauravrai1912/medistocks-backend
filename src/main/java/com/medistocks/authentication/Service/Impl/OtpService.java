package com.medistocks.authentication.Service.Impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
//import com.medistocks.authentication.DTO.EmailDetails;
//import com.medistocks.authentication.DTO.OtpRequest;
import com.medistocks.authentication.DTO.OtpResponse;
import com.medistocks.authentication.DTO.OtpValidationRequest;
import com.medistocks.authentication.DTO.Response;
import com.medistocks.authentication.Entity.Otp;
import com.medistocks.authentication.Repository.OtpRepository;
//import com.medistocks.authentication.Utils.AppUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Builder
public class OtpService {

        private final OtpRepository otpRepository;

        public Response validateOtp(OtpValidationRequest otpValidationRequest) {

                Otp otp = otpRepository.findByEmail(otpValidationRequest.getEmail());
                log.info("email: {}", otpValidationRequest.getEmail());
                if (otp == null) {
                        return Response.builder()
                                        .statusCode(400)
                                        .responseMessage("enter the otp")
                                        .build();
                }

                if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                        return Response.builder()
                                        .statusCode(400)
                                        .responseMessage("Otp Expired")
                                        .build();
                }

                if (!otp.getOtp().equals(otpValidationRequest.getOtp())) {
                        return Response.builder()
                                        .statusCode(400)
                                        .responseMessage("Invalid Otp")
                                        .build();

                }

                return Response.builder()
                                .statusCode(200)
                                .responseMessage("Success")
                                .otpResponse(OtpResponse.builder()
                                                .isOtpValid(true)
                                                .build())
                                .build();
        }

}
