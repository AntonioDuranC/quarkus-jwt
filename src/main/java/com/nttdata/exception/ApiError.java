package com.nttdata.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum ApiError {

    DAILY_LIMIT_EXCEEDED("ERR-001", "Límite de consultas diarias excedido", Response.Status.TOO_MANY_REQUESTS),
    EXTERNAL_API_ERROR("ERR-002", "Error al consumir la API externa de tipo de cambio", Response.Status.BAD_GATEWAY),
    INVALID_DNI("ERR-003", "El DNI es obligatorio y no puede estar vacío", Response.Status.BAD_REQUEST);

    private final String code;
    private final String message;
    private final Response.Status status;

    ApiError(String code, String message, Response.Status status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}