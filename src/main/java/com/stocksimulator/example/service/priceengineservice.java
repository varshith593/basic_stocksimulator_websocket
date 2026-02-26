package com.stocksimulator.example.service;

import com.stocksimulator.example.dto.outgoing;
import com.stocksimulator.example.entity.stock;
import com.stocksimulator.example.entity.stockprice;
import com.stocksimulator.example.entitymanger.stockinterface;
import com.stocksimulator.example.entitymanger.stockpriceinterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service // Tells Spring to manage this "Engine"
public class priceengineservice implements priceengine {

    private final stockpriceinterface spi;
    private final stockinterface si;
    private final Random random = new Random();
    private final SimpMessagingTemplate messagingTemplate;
    public priceengineservice(stockinterface si, stockpriceinterface spi, SimpMessagingTemplate messagingTemplate) {
        this.si = si;
        this.spi = spi;
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    @Transactional
    public void generateallstocks() {
        System.out.println("🔁 Generating all stocks...");
        List<stock> stocks = si.getstocks();
        System.out.println("Stocks found: " + stocks.size());

        for (stock s : stocks) {
            generatepriceperstock(s);
        }
    }

    @Override
    @Transactional
    public void generatepriceperstock(stock stock) {
        BigDecimal currentPrice = BigDecimal.valueOf(stock.getPrice());

        // 1. Logic for price change (-1% to +1%)
        double percentChange = (random.nextDouble() - 0.5) * 0.02;
        BigDecimal change = currentPrice.multiply(BigDecimal.valueOf(percentChange));
        BigDecimal newPrice = currentPrice.add(change).setScale(2, RoundingMode.HALF_UP);

        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            newPrice = BigDecimal.ONE;
        }

        System.out.println("📡 Broadcasting: " + stock.getSymbol());
        stock.setPrice(newPrice.doubleValue());
        si.updatestockprice(stock);


        stockprice history = new stockprice();
        history.setStock(stock);
        history.setStockprice(newPrice.longValue());
        history.setModified(LocalDateTime.now());
        spi.insert_price(history);


        outgoing update = new outgoing(stock.getId(), stock.getName(), stock.getSymbol(), newPrice, LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/stocks", update);
    }



    @Override
    @Transactional
    public void cleanoldprices(String symbol) {
        // Calls the Timestamp Window cleanup we wrote
        spi.delete_history_keep_last_60(symbol);
    }
    @Transactional
    public void cleanAllStocksHistory() {
        List<stock> stocks = si.getstocks();
        for (stock s : stocks) {
            spi.delete_history_keep_last_60(s.getSymbol());
        }
    }
}
