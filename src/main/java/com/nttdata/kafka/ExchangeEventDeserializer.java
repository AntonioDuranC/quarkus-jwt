package com.nttdata.kafka;

import com.nttdata.messaging.event.ExchangeQueryEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

/**
 * Este deserializador le dice a Kafka cómo convertir el JSON 
 * que viene del tópico 'exchange-topic' de vuelta a un objeto Java.
 */
public class ExchangeEventDeserializer extends ObjectMapperDeserializer<ExchangeQueryEvent> {
    
    public ExchangeEventDeserializer() {
        // Le pasamos la clase destino al constructor padre
        super(ExchangeQueryEvent.class);
    }
}