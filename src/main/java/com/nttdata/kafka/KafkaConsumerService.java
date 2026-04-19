package com.nttdata.kafka;

import com.nttdata.messaging.event.ExchangeQueryEvent;
import com.nttdata.models.ExchangeQueryRecord;
import com.nttdata.repositories.ExchangeQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumerService {

    private static final Logger LOG = Logger.getLogger(KafkaConsumerService.class);

    @Inject
    ExchangeQueryRepository repository;

    /**
     * Este método reemplaza el 'while(true)' y el 'poll'.
     * Quarkus escucha el canal 'exchange-in' (configurado en application.yml)
     * y entrega el evento ya deserializado gracias al ExchangeEventDeserializer.
     */
    @Incoming("exchange-in")
    @Transactional // Mantiene la transacción real para la BD
    public void consume(ExchangeQueryEvent event) {
        try {
            LOG.infof("📥 Mensaje recibido desde Kafka: DNI=%s", event.dni);

            // --- Conservamos tu lógica de guardado ---
            save(event);

        } catch (Exception e) {
            // Si algo falla, el mensaje no se confirma (NACK) y puede reintentarse
            LOG.error("❌ Error procesando mensaje de Kafka", e);
            throw e;
        }
    }

    /**
     * Mantenemos tu método save tal cual lo tenías,
     * asegurando la persistencia en el repositorio.
     */
    @Transactional
    public void save(ExchangeQueryEvent event) {
        ExchangeQueryRecord entity = new ExchangeQueryRecord(
                event.dni,
                event.queriedAt,
                event.queryDate,
                event.ratePen
        );

        repository.persist(entity);

        LOG.info("✅ Guardado en BD: " + entity);
    }
}