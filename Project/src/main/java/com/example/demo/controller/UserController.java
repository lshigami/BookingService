package com.example.demo.controller;

import com.example.demo.dto.request.auth.UpdateUserRequest;
import com.example.demo.dto.request.auth.UserCreatationRequest;
import com.example.demo.dto.response.APIResponse;
import com.example.demo.dto.response.auth.UserCreationResponse;
import com.example.demo.dto.response.auth.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.service.auth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public APIResponse createUser(@RequestBody @Valid UserCreatationRequest request) {
        UserCreationResponse user = userService.createUser(request);
        return new APIResponse(200, "Success", user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User>users(){
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public APIResponse getPersonalInfo(){
        return new APIResponse(200, "Success", userService.getPersonalInfo());
    }

    @PutMapping("/{id}")
    public APIResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        UserResponse user = userService.updateUser(id, request);
        return new APIResponse(200, "Success", user);
    }
}
