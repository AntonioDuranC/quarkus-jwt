package com.nttdata.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeQueryEvent {

    public String dni;

    public LocalDateTime queriedAt;

    public LocalDate queryDate;

    public Double ratePen;

}