package com.example.demo.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateUserRequest {
    private String password;
    private String firstname;
    private String lastname;
    private Date dob;
    List<String> roles;

}
