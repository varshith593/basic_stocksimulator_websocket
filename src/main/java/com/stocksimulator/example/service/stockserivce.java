package com.stocksimulator.example.service;

import com.stocksimulator.example.dto.incoming;
import com.stocksimulator.example.dto.outgoing;
import com.stocksimulator.example.entity.stock;
import com.stocksimulator.example.entitymanger.stockinterface;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class stockserivce implements stockserviceinterface {



    @Autowired
    private final stockinterface stocks;

    public stockserivce(stockinterface stocks) {
        this.stocks = stocks;
    }


    @Override
    public void createstock(incoming stock) {
        stock s = new stock();
        s.setName(stock.name());
        s.setSymbol(stock.symbol());
        s.setPrice(stock.price());
        stocks.createstock(s);


    }

    @Override
    public outgoing get_stock(String symbol) {
        stock s = stocks.getstockbysymbol(symbol);
        return new outgoing(
                s.getId(),
                s.getName(),
                s.getSymbol(),
                java.math.BigDecimal.valueOf(s.getPrice()),
                s.getCreatedDate()
        );
    }


    @Override
    public List<outgoing> get_stocks() {
        List<stock> allEntities = stocks.getstocks();

        // 2. Map the entities to your outgoing Record
        return allEntities.stream()
                .map(s -> new outgoing(
                        s.getId(),
                        s.getName(),
                        s.getSymbol(),
                        java.math.BigDecimal.valueOf(s.getPrice()),
                        s.getCreatedDate()
                ))
                .toList();
    }

    @Override
    public void delete_stock(String symbol) {
            stocks.deletestock(symbol);
    }

    @Override
    public   void update_stock(incoming stock,String name) {

       stock s = stocks.getstockbyname( name);
       s.setName(stock.name());
        s.setSymbol(stock.symbol());
        s.setPrice(stock.price());
        stocks.updatestockprice(s);



    }
}
