package com.stock.simulator;

import jakarta.persistence.*;

@Entity
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trader_id")
    private Trader trader;

    private String stockSymbol;
    private int quantity;

    // A no-args constructor is required for JPA
    public Holding() {
    }

    public Holding(Trader trader, String stockSymbol, int quantity) {
        this.trader = trader;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}