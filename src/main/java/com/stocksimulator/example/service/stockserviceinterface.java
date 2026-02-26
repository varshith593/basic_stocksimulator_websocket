package com.stocksimulator.example.service;

import com.stocksimulator.example.dto.incoming;
import com.stocksimulator.example.dto.outgoing;
import com.stocksimulator.example.entity.stock;

import java.util.List;


public interface stockserviceinterface {
    void createstock(incoming stock);
    outgoing get_stock(String name);
    List<outgoing> get_stocks();
    void delete_stock(String symbol);
    void update_stock(incoming stock,String name);

}
