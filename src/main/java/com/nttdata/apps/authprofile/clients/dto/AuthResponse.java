package com.nttdata.apps.authprofile.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
        @JsonProperty("access_token")
        String accesToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
