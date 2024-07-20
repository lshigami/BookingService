package com.example.demo.dto.response.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponse {
    private String name;
    private String description;
    Set<PermissionResponse> permissions;

}
