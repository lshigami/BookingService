package com.example.demo.repository.httpclient;


import com.example.demo.dto.request.oauth.ExchangeTokenRequest;
import com.example.demo.dto.response.oauth.ExchangeTokenResponse;
import com.example.demo.dto.response.oauth.OutboundUserResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-user", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OutboundUserResponse getUserInfo(@RequestParam("alt") String alt, @RequestParam("access_token") String acessToken);
}
