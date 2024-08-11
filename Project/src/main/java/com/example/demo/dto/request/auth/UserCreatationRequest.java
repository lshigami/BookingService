package com.example.demo.dto.request.auth;

import com.example.demo.exception.ErrorCode;
import com.example.demo.validator.DateOfBirthConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatationRequest {
    private String username;
    @Size(min=6,message = "PASSWORD_MUST_AT_LEAST_6_CHARACTERS")
    private String password;
    private String firstname;
    private String lastname;
    @DateOfBirthConstraint(min = 18, message ="INVALID_DATE_OF_BIRTH" )
    private Date dob;
}
