package com.example.demo.controller;

import com.example.demo.dto.request.auth.RoleRequest;
import com.example.demo.dto.response.APIResponse;
import com.example.demo.service.auth.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public APIResponse create(@RequestBody RoleRequest request){
        var response = roleService.create(request);
        log.info("Role created: {}", response);
        return new APIResponse(1000, "Success", response);
    }

    @GetMapping
    public APIResponse getAll(){
        return new APIResponse(1000, "Success", roleService.getAll());
    }
    @DeleteMapping("/{name}")
    public APIResponse deletePermission(@PathVariable String name){
        roleService.delete(name);
        return new APIResponse(1000, "Success", null);
    }

}
