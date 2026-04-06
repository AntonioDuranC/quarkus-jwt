package com.nttdata.resources;

import com.nttdata.dto.ExchangeRate;
import com.nttdata.exception.ApiError;
import com.nttdata.exception.CustomApiException;
import com.nttdata.security.Secured;
import com.nttdata.services.ExchangeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/exchange")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeResource {

    @Inject
    ExchangeService exchangeService;

    @GET
    @Path("/today")
    @Secured // 🔐 SOLO este endpoint queda protegido
    public Response getTodayExchange(@QueryParam("dni") String dni) {
        if (dni == null || dni.isBlank()) {
            throw new CustomApiException(ApiError.INVALID_DNI);
        }

        ExchangeRate result = exchangeService.getExchangeForDni(dni.trim());
        return Response.ok(result).build();
    }
}
