package com.stock.simulator.dto;

import com.stock.simulator.Trader;

import java.math.BigDecimal;

public class TraderDto {
    private Long id;
    private String name;
    private BigDecimal balance;

    public TraderDto(Trader trader) {
        this.id = trader.getId();
        this.name = trader.getName();
        this.balance = trader.getBalance();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}