package com.medistocks.authentication.DTO;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUser {

    private long phoneNumber;
    private String firstName;
    private String lastName;
    
}
