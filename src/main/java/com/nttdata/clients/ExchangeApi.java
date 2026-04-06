package com.nttdata.clients;

import com.nttdata.dto.TypeExchangeResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2.0/rates")
@RegisterRestClient(configKey = "currency-freaks")
public interface ExchangeApi {

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    TypeExchangeResponse getToday(@QueryParam("apikey") String apiKey);
}
