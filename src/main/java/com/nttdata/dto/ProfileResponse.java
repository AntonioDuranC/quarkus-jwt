package com.nttdata.dto;

public record ProfileResponse(
        String id,
        String email,
        String name,
        String role,
        String avatar
) {
}
