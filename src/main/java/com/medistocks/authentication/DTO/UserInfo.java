package com.medistocks.authentication.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    private UUID userId;
    private String email;
    private long phoneNumber;
    private String firstName;
    private String lastName;
    private String pharmacyName;   
}
