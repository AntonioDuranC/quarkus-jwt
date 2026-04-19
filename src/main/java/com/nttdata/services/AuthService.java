package com.nttdata.services;

import com.nttdata.clients.AuthClient;
import com.nttdata.dto.AuthRequest;
import com.nttdata.dto.AuthResponse;
import com.nttdata.dto.ProfileResponse;
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

    public AuthResponse login() {
        return authClient.login(new AuthRequest(
                email, password
        ));
    }

    public ProfileResponse getProfile() {
        var response = authClient.login(new AuthRequest(
                email, password
        ));
        //Verificamos que el token es válido
        return authClient.getProfile("Bearer " + response.accesToken());
    }
}
