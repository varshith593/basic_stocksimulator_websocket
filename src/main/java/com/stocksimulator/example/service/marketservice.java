package com.stocksimulator.example.service;

import com.stocksimulator.example.dto.outgoing;
import com.stocksimulator.example.entity.stockprice;
import com.stocksimulator.example.entitymanger.stockpriceinterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class marketservice implements marketanalytics{
    @Autowired
    private final stockpriceinterface spi;
    public marketservice(stockpriceinterface spi) {
        this.spi = spi;
    }


    @Override
    public long getAveragePrice(String symbol) {
        return spi.get_avg_prices(symbol);
    }

    @Override
    public List<outgoing> getlastnprices(String symbol, int n) {
        List<stockprice> stocknprices = spi.get_N_prices(symbol,n);
        return stocknprices.stream()
                .map(sp -> new outgoing(
                        sp.getId(),
                        sp.getStock().getName(),    // Reach into the Stock entity for the Name
                        sp.getStock().getSymbol(),  // Reach into the Stock entity for the Symbol
                        java.math.BigDecimal.valueOf(sp.getStockprice()), // Convert long to BigDecimal
                        sp.getModified()            // Use the price timestamp
                ))
                .toList();
    }

    @Override
    public long getlastprice(String symbol) {
        return 0;
    }

    @Override
    public long maxprice(String symbol) {
        return 0;
    }

    @Override
    public long minprice(String symbol) {
        return 0;
    }
}
