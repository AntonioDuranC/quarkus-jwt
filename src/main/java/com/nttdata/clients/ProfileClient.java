package com.nttdata.clients;

import com.nttdata.dto.ProfileResponse;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v1/auth")
@RegisterRestClient(configKey = "escuelajs-api")
public interface ProfileClient {

    @Path("/profile")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    ProfileResponse getProfile(@HeaderParam("Authorization") String authorization);
}
