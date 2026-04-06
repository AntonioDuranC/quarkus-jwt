package com.nttdata.services;

import com.nttdata.clients.ExchangeApi;
import com.nttdata.dto.ExchangeRate;
import com.nttdata.dto.TypeExchangeResponse;
import com.nttdata.exception.ApiError;
import com.nttdata.exception.CustomApiException;
import com.nttdata.models.ExchangeQueryRecord;
import com.nttdata.repositories.ExchangeQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApplicationScoped
public class ExchangeService {

    private static final Logger LOG = Logger.getLogger(ExchangeService.class);

    private static final int DAILY_LIMIT = 10;

    @Inject
    ExchangeQueryRepository repository;

    @Inject
    @RestClient
    ExchangeApi exchangeApi;

    @ConfigProperty(name = "currency.freaks.api-key")
    String apiKey;

    @Transactional
    public ExchangeRate getExchangeForDni(String dni) {
        LocalDate today = LocalDate.now();
        long count = repository.countByDniAndDate(dni, today);

        if (count >= DAILY_LIMIT) {
            throw new CustomApiException(ApiError.DAILY_LIMIT_EXCEEDED);
        }

        try {
            TypeExchangeResponse response = exchangeApi.getToday(apiKey);

            LOG.infof("Response recibido: date=%s, PEN=%s",
                    response.getDate(),
                    response.getRate("PEN"));

            return new ExchangeRate(
                    response.getDate(),
                    response.getRate("PEN")
            );

        } catch (Exception e) {
            LOG.error("Error consumiendo API externa", e);
            throw new CustomApiException(ApiError.EXTERNAL_API_ERROR);
        } finally {
            repository.persist(
                    new ExchangeQueryRecord(dni, LocalDateTime.now(), today)
            );
        }
    }
}