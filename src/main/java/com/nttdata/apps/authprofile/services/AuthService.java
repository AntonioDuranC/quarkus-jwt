package com.nttdata.apps.authprofile.services;

import com.nttdata.apps.authprofile.clients.AuthClient;
import com.nttdata.apps.authprofile.clients.ProfileClient;
import com.nttdata.apps.authprofile.clients.dto.AuthRequest;
import com.nttdata.apps.authprofile.clients.dto.AuthResponse;
import com.nttdata.apps.authprofile.clients.dto.ProfileResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "application.auth-api.email")
    String email;

    @ConfigProperty(name = "application.auth-api.password")
    String password;

    @Inject
    @RestClient
    AuthClient authClient;

    @Inject
    @RestClient
    ProfileClient profileClient;

    public AuthResponse login() {
        return authClient.login(new AuthRequest(
                email, password
        ));
    }

    public ProfileResponse getProfile() {
        var response = authClient.login(new AuthRequest(
                email, password
        ));

        return profileClient.getProfile("Bearer " + response.accesToken());
    }
}
