package com.nttdata.clients;

import com.nttdata.dto.AuthRequest;
import com.nttdata.dto.AuthResponse;
import com.nttdata.dto.ProfileResponse;
import jakarta.ws.rs.*;
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


    @Path("/profile")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    ProfileResponse getProfile(@HeaderParam("Authorization") String authorization);
}
