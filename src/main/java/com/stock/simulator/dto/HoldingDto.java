package com.stock.simulator.dto;

public class HoldingDto {
    private String stockSymbol;
    private int quantity;

    // A no-args constructor is required for Spring serialization
    public HoldingDto() {
    }

    public HoldingDto(String stockSymbol, int quantity) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
    }

    // Getters and Setters
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