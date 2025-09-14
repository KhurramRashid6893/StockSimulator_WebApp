package com.stock.simulator.dto;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioDto {
    private BigDecimal balance;
    private List<HoldingDto> holdings;

    // This constructor is used by the application logic
    public PortfolioDto(BigDecimal balance, List<HoldingDto> holdings) {
        this.balance = balance;
        this.holdings = holdings;
    }

    // A no-args constructor is required for Spring serialization
    public PortfolioDto() {
    }

    // Getters and Setters
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<HoldingDto> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldingDto> holdings) {
        this.holdings = holdings;
    }
}