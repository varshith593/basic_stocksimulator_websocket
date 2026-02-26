package com.stocksimulator.example;

import com.stocksimulator.example.entity.stock;
import com.stocksimulator.example.entitymanger.stockinterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Bean
    CommandLineRunner initStocks(stockinterface si) {
        return args -> {
            // Only insert if the table is empty to avoid "Unique Constraint" errors
            if (si.getstocks().isEmpty()) {

                si.createstock(create("Apple Inc.", "AAPL", 185.50));
                si.createstock(create("Nvidia Corp.", "NVDA", 720.15));
                si.createstock(create("Tesla Inc.", "TSLA", 195.20));
                si.createstock(create("Microsoft", "MSFT", 405.30));
                si.createstock(create("Amazon.com", "AMZN", 175.10));

                System.out.println("✅ 5 Stocks seeded! Scheduler will now start generating prices.");
            }
        };
    }

    // Helper to create the object quickly
    private stock create(String name, String symbol, double price) {
        stock s = new stock();
        s.setName(name);
        s.setSymbol(symbol);
        s.setPrice(price);
        return s;
    }

}