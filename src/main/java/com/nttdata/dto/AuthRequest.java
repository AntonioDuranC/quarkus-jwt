package com.nttdata.dto;

public record AuthRequest(
        String email,
        String password
) {
}
