package com.nttdata.security;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TokenService {

    private final Map<String, LocalDateTime> validTokens = new ConcurrentHashMap<>();

    private static final long EXPIRATION_MINUTES = 10;

    public void register(String token) {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        validTokens.put(token, expiration);
    }

    public boolean validate(String token) {

        LocalDateTime expiration = validTokens.get(token);

        if (expiration == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(expiration)) {
            validTokens.remove(token);
            return false;
        }

        return true;
    }
}