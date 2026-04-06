package com.nttdata.apps.authprofile.clients.dto;

public record AuthRequest(
        String email,
        String password
) {
}
