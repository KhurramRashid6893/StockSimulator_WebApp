package com.stock.simulator.controller;

import com.stock.simulator.Stock;
import com.stock.simulator.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    private StockMarketService stockMarketService;

    @GetMapping
    public List<Stock> getAllStocks(@RequestParam(required = false) String search) {
        return stockMarketService.getAllStocks(search);
    }

    @PostMapping("/refresh")
    public void refreshPrices() {
        stockMarketService.updateAllPrices();
    }
}