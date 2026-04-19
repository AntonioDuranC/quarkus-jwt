package com.nttdata.resources;

import com.nttdata.security.Secured;
import com.nttdata.security.TokenService;
import com.nttdata.services.AuthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/api/profile")
public class ProfileResource {

    private static final Logger LOG = Logger.getLogger(ProfileResource.class);

    @Inject
    AuthService authService;

    @Inject
    TokenService tokenService;

    @GET
    @Path("/token")
    public Response getToken() {
        LOG.info("Entramos al endpoint /token...");

        String token = authService.login().accesToken();

        tokenService.register(token);
        return Response.ok(token).build();
    }

    @GET
    @Path("/data")
    @Secured
    public Response getProfileData() {
        LOG.info("Entramos al endpoint /data...");

        //Verificamos el token que se genero en el endpoint: /api/profile/token
        return Response.ok(authService.getProfile()
        ).build();
    }
}
