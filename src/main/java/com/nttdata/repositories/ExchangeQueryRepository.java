package com.nttdata.repositories;

import com.nttdata.models.ExchangeQueryRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class ExchangeQueryRepository implements PanacheRepository<ExchangeQueryRecord> {

    public long countByDniAndDate(String dni, LocalDate date) {
        return count("dni = ?1 and queryDate = ?2", dni, date);
    }
}
