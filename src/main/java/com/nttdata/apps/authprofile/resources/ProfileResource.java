package com.nttdata.apps.authprofile.resources;

import com.nttdata.apps.authprofile.services.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/profile")
public class ProfileResource {

    @Inject
    AuthService authService;

    @GET
    @Path("/token")
    public Response getToken() {
        return Response.ok(
                authService.login().accesToken()
        ).build();
    }

    @GET
    @Path("/data")
    public Response getProfileData() {
        return Response.ok(
                authService.getProfile()
        ).build();
    }
}
