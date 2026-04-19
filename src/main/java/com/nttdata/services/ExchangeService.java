package com.nttdata.services;

import com.nttdata.clients.ExchangeApi;
import com.nttdata.dto.ExchangeRate;
import com.nttdata.dto.TypeExchangeResponse;
import com.nttdata.exception.ApiError;
import com.nttdata.exception.CustomApiException;
import com.nttdata.messaging.event.ExchangeQueryEvent;
import com.nttdata.kafka.KafkaProducerService;
import com.nttdata.repositories.ExchangeQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

    @Inject
    KafkaProducerService producer; // Ahora inyecta el productor reactivo

    public ExchangeRate getExchangeForDni(String dni) {

        LocalDate today = LocalDate.now();

        // Validación de límite (Consulta directa a BD)
        long count = repository.countByDniAndDate(dni, today);

        if (count >= DAILY_LIMIT) {
            throw new CustomApiException(ApiError.DAILY_LIMIT_EXCEEDED);
        }

        try {
            // Llamada a API externa
            TypeExchangeResponse response = exchangeApi.getToday(apiKey);
            Double ratePen = response.getRate("PEN");

            LOG.infof("Response recibido: date=%s, PEN=%s", response.getDate(), ratePen);

            // 🔥 DISPARAR Y OLVIDAR (Fire and Forget)
            // Publicamos en Kafka de forma asíncrona.
            // El usuario recibe su respuesta sin esperar a que la BD termine de guardar.
            publishEvent(dni, today, ratePen);

            return new ExchangeRate(
                    response.getDate(),
                    ratePen
            );

        } catch (Exception e) {
            LOG.error("Error consumiendo API externa", e);
            throw new CustomApiException(ApiError.EXTERNAL_API_ERROR);
        }
    }

    private void publishEvent(String dni, LocalDate today, Double rate) {
        // Creamos el evento con los datos obtenidos
        ExchangeQueryEvent event = new ExchangeQueryEvent(
                dni,
                LocalDateTime.now(),
                today,
                rate
        );

        // Enviamos al productor reactivo
        producer.send(event);
    }
}