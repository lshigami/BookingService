package com.example.demo.service.auth;

import com.example.demo.repository.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TokenCleanUpService {
    @Autowired
    private InvalidatedTokenRepository invalidatedRepository;
    @Scheduled(cron = "0 18 17 * * ?")
    @Transactional
    public void removeExpiredTokens() {
        System.out.println("Removing expired tokens");
        invalidatedRepository.deleteByExpiryDateBefore(new Date());
    }
}
