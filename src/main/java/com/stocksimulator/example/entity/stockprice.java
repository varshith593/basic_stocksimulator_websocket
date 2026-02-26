package com.stocksimulator.example.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class stockprice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private stock stock;
    @Column(nullable = false)
    private long stockprice;

    @LastModifiedDate
    private LocalDateTime modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public stock getStock() {
        return stock;
    }

    public void setStock(stock stock) {
        this.stock = stock;
    }

    public long getStockprice() {
        return stockprice;
    }

    public void setStockprice(long stockprice) {
        this.stockprice = stockprice;
    }
}
