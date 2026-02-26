package com.stocksimulator.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record outgoing(
        Long id,
        String name,
        String symbol,
        BigDecimal currentPrice,
        LocalDateTime createdDate
) {
    @Override
    public LocalDateTime createdDate() {
        return createdDate;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public BigDecimal currentPrice() {
        return currentPrice;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String symbol() {
        return symbol;
    }
}
