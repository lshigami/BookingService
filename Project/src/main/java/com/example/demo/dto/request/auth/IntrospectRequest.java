package com.example.demo.dto.request.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectRequest {
    private String token;
}
