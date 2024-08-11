package com.example.demo.dto.request.auth;

import lombok.Getter;

import java.util.Set;

@Getter
public class RoleRequest {
    private String name;
    private String description;
    Set<String> permissions;

}
