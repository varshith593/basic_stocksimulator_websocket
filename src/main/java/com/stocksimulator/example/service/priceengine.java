package com.stocksimulator.example.service;

import com.stocksimulator.example.entity.stock;

public interface priceengine {
    public void generateallstocks();
    void generatepriceperstock(stock stock);

    void cleanoldprices(String name);
    void cleanAllStocksHistory();
}
