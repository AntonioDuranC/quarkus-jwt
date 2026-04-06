package com.nttdata.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TypeExchangeResponse {

    private String date;
    private String base;

    private Map<String, String> rates;

    public Double getRate(String currency) {
        if (rates == null || !rates.containsKey(currency)) {
            return null;
        }
        return Double.valueOf(rates.get(currency));
    }
}