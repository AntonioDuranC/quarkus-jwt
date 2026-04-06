package com.nttdata.apps.authprofile.clients;

import com.nttdata.apps.authprofile.clients.dto.AuthRequest;
import com.nttdata.apps.authprofile.clients.dto.AuthResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v1/auth")
@RegisterRestClient(configKey = "escuelajs-api")
public interface AuthClient {

    @POST
    @Path("/login")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    AuthResponse login(AuthRequest request);
}
