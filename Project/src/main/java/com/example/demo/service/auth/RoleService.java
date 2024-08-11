package com.example.demo.service.auth;


import com.example.demo.converter.PermissionConverter;
import com.example.demo.converter.RoleConverter;
import com.example.demo.dto.request.auth.RoleRequest;
import com.example.demo.dto.response.auth.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleConverter roleConverter;
    PermissionConverter permissionConverter;

    public RoleResponse create(RoleRequest request) {
        Role role = roleConverter.convertToEntity(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        RoleResponse response = roleConverter.convertToResponse(role);
        response.setPermissions(permissions.stream().map(permissionConverter::convertToResponse).collect(Collectors.toSet()));
        return roleConverter.convertToResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleConverter::convertToResponse).collect(Collectors.toList());
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }


}
