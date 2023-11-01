package com.example.theagenda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    String firstname;
    String lastname;
    String phoneNumber;
    String email;
    String role;
    String token;
}
