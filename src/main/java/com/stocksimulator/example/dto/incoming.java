package com.stocksimulator.example.dto;
import java.math.BigDecimal;
public record incoming(


/**
 * Record for basic stock data input
 */
        String name,
        String symbol,
        Double price

) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public Double price() {
        return price;
    }
}
