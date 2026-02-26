package com.stocksimulator.example.controller;

import com.stocksimulator.example.dto.incoming;
import com.stocksimulator.example.dto.outgoing;
import com.stocksimulator.example.service.marketanalytics;
import com.stocksimulator.example.service.priceengine;
import com.stocksimulator.example.service.stockserviceinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class controller1 {
    //
    private final marketanalytics ma;
    private final stockserviceinterface ssi;
    public controller1(priceengine pe,  stockserviceinterface ssi, marketanalytics ma) {
        this.ssi = ssi;
        this.ma = ma;
    }
    @GetMapping("/allstocks")
    public List<outgoing> get_stocks() {
        return ssi.get_stocks();
    }

    @GetMapping("/{symbol}/get_stock")
    public outgoing get_stock(@PathVariable String symbol) {
        return ssi.get_stock(symbol);
    }

    @GetMapping("/{symbol}/average")
    public long avgprice(@PathVariable String symbol) {
        return ma.getAveragePrice(symbol);
    }

    @GetMapping("/nprices/{symbol}/{n}")
    public List<outgoing> nprices(@PathVariable String symbol, // Extracted from ?symbol=AAPL
                                  @PathVariable int n    ) {
        return ma.getlastnprices(symbol, n);
    }
    //here i done the only we can send the one json post at a time if you want bulk without time waste
    //at the @Requestbody List<incoming> stock and in the service make a methos that accepts the list<incoming> and make for loop grab each stock and conver ot into the stock entity and save it in that loop only by calling entity manger method

    @PostMapping("/sendstock")
    public String sendstock(@RequestBody incoming stock) {
        ssi.createstock(stock);
        return "success";
    }
    @DeleteMapping("/delete/{symbol}")
    public String deletestock(@PathVariable String symbol) {
        ssi.delete_stock(symbol);
        return "success";
    }






}
