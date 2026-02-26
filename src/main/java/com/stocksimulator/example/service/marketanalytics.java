package com.stocksimulator.example.service;

import com.stocksimulator.example.dto.outgoing;

import java.util.List;

public interface marketanalytics {
    long getAveragePrice(String symbol);
    List<outgoing> getlastnprices(String symbol, int n);
    long getlastprice(String symbol);
    long maxprice(String symbol);
    long minprice(String symbol);
}
