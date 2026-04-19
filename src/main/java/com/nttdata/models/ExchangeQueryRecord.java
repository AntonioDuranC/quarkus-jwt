package com.nttdata.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_query_record")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeQueryRecord extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "dni", nullable = false, length = 8)
    public String dni;

    @Column(name = "queried_at", nullable = false)
    public LocalDateTime queriedAt;

    @Column(name = "query_date", nullable = false)
    public LocalDate queryDate;

    @Column(name = "rate", nullable = false)
    public Double ratePen;

    public ExchangeQueryRecord(String dni, LocalDateTime queriedAt, LocalDate queryDate, Double ratePen) {
        this.dni = dni;
        this.queriedAt = queriedAt;
        this.queryDate = queryDate;
        this.ratePen = ratePen;
    }

    @Override
    public String toString() {
        return "ExchangeQueryRecord{" +
                "dni='" + dni + '\'' +
                ", queriedAt=" + queriedAt +
                ", queryDate=" + queryDate +
                ", ratePen=" + ratePen +
                '}';
    }
}
