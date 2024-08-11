package com.example.demo.controller;

import com.example.demo.dto.request.auth.UserCreatationRequest;
import com.example.demo.dto.response.auth.UserCreationResponse;
import com.example.demo.dto.response.auth.UserResponse;
import com.example.demo.service.auth.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean // This annotation is used to add mocks to a Spring ApplicationContext.
    private UserService userService;

    private UserCreatationRequest request;
    private UserCreationResponse response;

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
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // when
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        request.setPassword("123");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1005))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Password must be at least 6 characters")
                );
    }


}
