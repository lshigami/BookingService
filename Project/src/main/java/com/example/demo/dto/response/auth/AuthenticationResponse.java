package com.example.demo.dto.response.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
