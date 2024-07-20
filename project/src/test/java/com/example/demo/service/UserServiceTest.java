package com.example.demo.service;


import com.example.demo.dto.request.auth.UserCreatationRequest;
import com.example.demo.dto.response.auth.UserCreationResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.auth.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    private UserCreatationRequest request;
    private UserCreationResponse response;
    private User user;
    @BeforeEach
    public void setUp() {
        request = UserCreatationRequest.builder()
                .username("test")
                .password("test123456")
                .firstname("test")
                .lastname("test")
                .dob(Date.from(LocalDate.of(1999, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        response = UserCreationResponse.builder()
                .username("test")
                .firstname("test")
                .lastname("test")
                .dob(Date.from(LocalDate.of(1999, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        user= User.builder()
                .username("test")
                .firstname("test")
                .lastname("test")
                .dob(Date.from(LocalDate.of(1999, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }
    @Test
    void createUser_validRequest_success() {
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        var response = userService.createUser(request);
        assert response.getUsername().equals("test");
    }
}
