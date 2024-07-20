package com.example.demo.service.auth;


import com.example.demo.converter.PermissionConverter;
import com.example.demo.dto.request.auth.PermissionRequest;
import com.example.demo.dto.response.auth.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionService {
    PermissionRepository permissionRepository;
PermissionConverter permissionConverter;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionConverter.convertToEntity(request);
        permissionRepository.save(permission);
        return permissionConverter.convertToResponse(permission);
    }
    public List<PermissionResponse>getAll(){
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionConverter::convertToResponse).toList();
    }
    public void delete(String name){
        permissionRepository.deleteById(name);
    }
}
