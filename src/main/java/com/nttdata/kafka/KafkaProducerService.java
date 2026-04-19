package com.nttdata.kafka;

import com.nttdata.messaging.event.ExchangeQueryEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class KafkaProducerService {

    private static final Logger LOG = Logger.getLogger(KafkaProducerService.class);

    // Inyectamos el canal de salida definido en el application.yml
    @Inject
    @Channel("exchange-out")
    Emitter<ExchangeQueryEvent> emitter;

    public void send(ExchangeQueryEvent event) {
        LOG.infof("📤 Preparando envío a Kafka: DNI=%s", event.dni);

        try {
            // El envío es asíncrono y devuelve un CompletionStage
            emitter.send(event)
                    .toCompletableFuture()
                    .thenAccept(v -> LOG.infof("✅ Mensaje confirmado por Kafka para DNI: %s", event.dni))
                    .exceptionally(ex -> {
                        LOG.error("❌ Error en el callback de Kafka", ex);
                        return null;
                    });

        } catch (Exception e) {
            LOG.error("❌ Error enviando evento", e);
        }
    }
}