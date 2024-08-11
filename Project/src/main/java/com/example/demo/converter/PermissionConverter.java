package com.example.demo.converter;

import com.example.demo.dto.request.auth.PermissionRequest;
import com.example.demo.dto.response.auth.PermissionResponse;
import com.example.demo.entity.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {
    @Autowired
    private ModelMapper modelMapper;
    public Permission convertToEntity(PermissionRequest request){
        return modelMapper.map(request, Permission.class);
    }
    public PermissionResponse convertToResponse(Permission permission){
        return modelMapper.map(permission, PermissionResponse.class);
    }
}
