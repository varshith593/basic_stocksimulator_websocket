package com.stocksimulator.example.entitymanger;
import com.stocksimulator.example.entity.stockprice;
import jakarta.transaction.Transactional;

import java.util.List;

public interface stockpriceinterface {
        void insert_price(stockprice stockprice);
        long get_price(long id);


    List<stockprice> get_N_prices(String symbol, int n);

    List<stockprice> get_time_prices(String symbol);
        long get_avg_prices(String symbol);
        @Transactional
         void delete_history_keep_last_60(String symbol);

    long maxprice(String symbol);

    long minprice(String symbol);
}
