package com.example.demo.service.auth;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.request.auth.UpdateUserRequest;
import com.example.demo.dto.request.auth.UserCreatationRequest;
import com.example.demo.dto.response.auth.UserCreationResponse;
import com.example.demo.dto.response.auth.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;

    UserConverter userConverter;

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserCreationResponse createUser(UserCreatationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = userConverter.convertToEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        List<Role> roles = roleRepository.findAllById(request.getRoles());
        UserCreationResponse response = userConverter.convertToUserCreationResponse(userRepository.save(user));
        return response;
    }

    public UserCreationResponse getPersonalInfo() {
        var context = SecurityContextHolder.getContext();
        User user = userRepository.findByUsername(context.getAuthentication().getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userConverter.convertToUserCreationResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name || hasRole('ADMIN')")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userConverter.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role>roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return userConverter.convertToUserResponse(user);
    }
}
