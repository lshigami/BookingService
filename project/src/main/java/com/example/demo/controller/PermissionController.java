package com.example.demo.controller;


import com.example.demo.dto.request.auth.PermissionRequest;
import com.example.demo.dto.response.APIResponse;
import com.example.demo.service.auth.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public APIResponse createPermission(@RequestBody PermissionRequest request){
        return new APIResponse(1000, "Success", permissionService.create(request));
    }

    @GetMapping
    public APIResponse getAllPermissions(){
        return new APIResponse(1000, "Success", permissionService.getAll());
    }
    @DeleteMapping("/{name}")
    public APIResponse deletePermission(@PathVariable String name){
        permissionService.delete(name);
        return new APIResponse(1000, "Success", null);
    }


}
