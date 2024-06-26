package com.medistocks.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    
    private int employeeId;
    private String email;
    private long phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String pharmacyName;

}
