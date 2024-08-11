package com.example.demo.dto.response.auth;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationResponse {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Date dob;
    private String roles;
}
