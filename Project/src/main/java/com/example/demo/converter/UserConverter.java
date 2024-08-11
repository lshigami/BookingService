package com.example.demo.converter;

import com.example.demo.dto.request.auth.UpdateUserRequest;
import com.example.demo.dto.request.auth.UserCreatationRequest;
import com.example.demo.dto.response.auth.UserCreationResponse;
import com.example.demo.dto.response.auth.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    public User convertToEntity(UserCreatationRequest request){
        User result = modelMapper.map(request, User.class);
        return result;
    }
    public UserCreationResponse convertToUserCreationResponse(User user){
        UserCreationResponse result = modelMapper.map(user, UserCreationResponse.class);
        return result;
    }
    public UserResponse convertToUserResponse(User user){
       UserResponse result = modelMapper.map(user, UserResponse.class);
        result.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
       return result;
    }
    public void updateUser(User user, UpdateUserRequest request){
        modelMapper.map(request, user);
    }
}
