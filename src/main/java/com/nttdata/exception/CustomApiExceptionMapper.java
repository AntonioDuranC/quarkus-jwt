package com.nttdata.exception;

import com.nttdata.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomApiExceptionMapper implements ExceptionMapper<CustomApiException> {

    @Override
    public Response toResponse(CustomApiException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getCode(),
                exception.getMessage()
        );

        return Response.status(exception.getStatus())
                .entity(errorResponse)
                .build();
    }
}