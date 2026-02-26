
package com.stocksimulator.example.scheduler;

import com.stocksimulator.example.service.priceengine;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketScheduler {

    private final priceengine engine;

    public MarketScheduler(priceengine engine) {
        this.engine = engine;
    }


    @Scheduled(fixedRate = 1000)
    public void runMarketTick() {
        System.out.println("⏱ Market tick running...");
        engine.generateallstocks();
    }


    @Scheduled(fixedRate = 60000)
    public void cleanupHistory() {
        engine.cleanAllStocksHistory();
    }
}