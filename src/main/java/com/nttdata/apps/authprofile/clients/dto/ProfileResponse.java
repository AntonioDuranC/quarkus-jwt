package com.nttdata.apps.authprofile.clients.dto;

public record ProfileResponse(
        String id,
        String email,
        String name,
        String role,
        String avatar
) {
}
