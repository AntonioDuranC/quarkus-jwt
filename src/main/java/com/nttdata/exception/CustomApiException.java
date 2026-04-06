package com.nttdata.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class CustomApiException extends WebApplicationException {

    private final String code;
    private final Response.Status status;

    public CustomApiException(ApiError error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.status = error.getStatus();
    }

}
