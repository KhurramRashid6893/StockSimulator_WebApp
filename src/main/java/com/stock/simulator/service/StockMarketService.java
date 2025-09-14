package com.stock.simulator.service;

import com.stock.simulator.Stock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class StockMarketService {
    private static final Map<String, Stock> availableStocks = new HashMap<>();
    private static final Random random = new Random();

    static {
        availableStocks.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", new BigDecimal("145.50")));
        availableStocks.put("AAPL", new Stock("AAPL", "Apple Inc.", new BigDecimal("180.25")));
        availableStocks.put("MSFT", new Stock("MSFT", "Microsoft Corp.", new BigDecimal("320.75")));
        availableStocks.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", new BigDecimal("135.90")));
    }

    public List<Stock> getAllStocks(String searchTerm) {
        return availableStocks.values().stream()
                .filter(stock -> searchTerm == null || searchTerm.isBlank() || 
                                 stock.getSymbol().toLowerCase().contains(searchTerm.toLowerCase()) || 
                                 stock.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Stock getStock(String symbol) {
        return availableStocks.get(symbol.toUpperCase());
    }

    public void updateAllPrices() {
        for (Stock stock : availableStocks.values()) {
            double change = (random.nextDouble() * 0.1) - 0.05; // -5% to +5% change
            BigDecimal newPrice = stock.getPrice().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(change)));
            stock.setPrice(newPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }
}