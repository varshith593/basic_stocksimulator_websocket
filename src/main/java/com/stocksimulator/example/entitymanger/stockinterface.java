package com.stocksimulator.example.entitymanger;

import com.stocksimulator.example.entity.stock;

import java.util.List;


public interface stockinterface {

    void createstock(stock stock);
    void updatestockprice(stock stock);
    void deletestock(String symbol);
    List<stock> getstocks();
    stock getstockbyid(long id);
    stock getstockbyname(String name);
    stock getstockbysymbol(String symbol);

}
