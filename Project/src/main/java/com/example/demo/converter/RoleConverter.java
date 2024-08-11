package com.example.demo.converter;

import com.example.demo.dto.request.auth.RoleRequest;
import com.example.demo.dto.response.auth.RoleResponse;
import com.example.demo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
    @Autowired
    private ModelMapper modelMapper;

    public Role convertToEntity(RoleRequest request){
        return modelMapper.map(request, Role.class);
    }
    public RoleResponse convertToResponse(Role role){
        return modelMapper.map(role, RoleResponse.class);
    }
}
