package com.example.demo.controller;

import com.example.demo.dto.request.auth.AuthenticationRequest;
import com.example.demo.dto.request.auth.IntrospectRequest;
import com.example.demo.dto.request.auth.LogOutRequest;
import com.example.demo.dto.request.auth.RefreshRequest;
import com.example.demo.dto.response.APIResponse;
import com.example.demo.dto.response.auth.AuthenticationResponse;
import com.example.demo.service.auth.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Slf4j
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/outbound/authentication")
    public APIResponse outboundAuthentication(@RequestParam("code") String code){
        log.info("code controller: {}", code);
        AuthenticationResponse authenticationResponse = authenticationService.outboundAuthentication(code);
        log.info("authenticationResponse: {}", authenticationResponse);
        return  APIResponse.builder().code(1000).data(authenticationResponse).build();
    }
    @PostMapping("/signin")
    APIResponse authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return  APIResponse.builder().code(1000).data(authenticationResponse).build();
    }
    @PostMapping("/introspect")
    APIResponse authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var introspectResponse = authenticationService.introspect(request);
        return  APIResponse.builder().code(1000).data(introspectResponse).build();
    }

    @PostMapping("/logout")
    public APIResponse logout(@RequestBody LogOutRequest request)throws ParseException, JOSEException{
        authenticationService.logout(request);
        return APIResponse.builder().code(1000).message("LOGOUT_SUCCESS").build();
    }

    @PostMapping("/refresh")
    public APIResponse refresh(@RequestBody RefreshRequest request)throws ParseException, JOSEException{
        AuthenticationResponse token = authenticationService.refreshToken(request);
        return APIResponse.builder().code(1000).data(token).build();
    }

}
