package com.stock.simulator.controller;

import com.stock.simulator.Trader;
import com.stock.simulator.dto.HoldingDto;
import com.stock.simulator.dto.PortfolioDto;
import com.stock.simulator.dto.TradeRequestDto;
import com.stock.simulator.dto.TraderDto;
import com.stock.simulator.repository.HoldingRepository;
import com.stock.simulator.repository.TraderRepository;
import com.stock.simulator.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/traders")
public class TraderController {
    @Autowired private TraderRepository traderRepository;
    @Autowired private HoldingRepository holdingRepository;
    @Autowired private TradeService tradeService;

    @GetMapping("/login")
    public ResponseEntity<TraderDto> login(@RequestParam String name) {
        Optional<Trader> traderOptional = traderRepository.findByName(name);
        return traderOptional
                .map(trader -> new ResponseEntity<>(new TraderDto(trader), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<TraderDto> createTrader(@RequestBody Trader trader) {
        Optional<Trader> existingTrader = traderRepository.findByName(trader.getName());
        if(existingTrader.isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Trader newTrader = traderRepository.save(trader);
        return new ResponseEntity<>(new TraderDto(newTrader), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraderDto> getTrader(@PathVariable Long id) {
        return traderRepository.findById(id)
                .map(trader -> new ResponseEntity<>(new TraderDto(trader), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/{id}/portfolio")
    public ResponseEntity<PortfolioDto> getPortfolio(@PathVariable Long id) {
        Trader trader = traderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));

        List<HoldingDto> holdings = holdingRepository.findByTraderId(id).stream()
                .map(holding -> new HoldingDto(holding.getStockSymbol(), holding.getQuantity()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new PortfolioDto(trader.getBalance(), holdings), HttpStatus.OK);
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<Void> buyStock(@PathVariable Long id, @RequestBody TradeRequestDto tradeRequest) {
        tradeService.buyStock(id, tradeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/sell")
    public ResponseEntity<Void> sellStock(@PathVariable Long id, @RequestBody TradeRequestDto tradeRequest) {
        tradeService.sellStock(id, tradeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}