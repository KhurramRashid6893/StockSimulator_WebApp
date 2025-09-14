package com.stock.simulator.service;

import com.stock.simulator.Holding;
import com.stock.simulator.Stock;
import com.stock.simulator.Trader;
import com.stock.simulator.Transaction;
import com.stock.simulator.dto.TradeRequestDto;
import com.stock.simulator.repository.HoldingRepository;
import com.stock.simulator.repository.TraderRepository;
import com.stock.simulator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class TradeService {
    @Autowired private TraderRepository traderRepository;
    @Autowired private HoldingRepository holdingRepository;
    @Autowired private StockMarketService stockMarketService;
    @Autowired private TransactionRepository transactionRepository;

    public void buyStock(Long traderId, TradeRequestDto tradeRequest) {
        Trader trader = traderRepository.findById(traderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));

        Stock stock = stockMarketService.getStock(tradeRequest.getStockSymbol());
        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
        }

        BigDecimal cost = stock.getPrice().multiply(new BigDecimal(tradeRequest.getQuantity()));
        if (trader.getBalance().compareTo(cost) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        trader.setBalance(trader.getBalance().subtract(cost));
        traderRepository.save(trader);
        
        // Log the transaction
        transactionRepository.save(new Transaction(trader, stock.getSymbol(), "BUY", tradeRequest.getQuantity(), stock.getPrice()));

        Optional<Holding> existingHolding = holdingRepository.findByTraderIdAndStockSymbol(traderId, stock.getSymbol());
        if (existingHolding.isPresent()) {
            Holding holding = existingHolding.get();
            holding.setQuantity(holding.getQuantity() + tradeRequest.getQuantity());
            holdingRepository.save(holding);
        } else {
            Holding newHolding = new Holding(trader, stock.getSymbol(), tradeRequest.getQuantity());
            holdingRepository.save(newHolding);
        }
    }

    public void sellStock(Long traderId, TradeRequestDto tradeRequest) {
        Trader trader = traderRepository.findById(traderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));

        Stock stock = stockMarketService.getStock(tradeRequest.getStockSymbol());
        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
        }

        Optional<Holding> holdingOptional = holdingRepository.findByTraderIdAndStockSymbol(traderId, stock.getSymbol());
        if (holdingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not own this stock");
        }

        Holding holding = holdingOptional.get();
        if (holding.getQuantity() < tradeRequest.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough shares to sell");
        }

        BigDecimal proceeds = stock.getPrice().multiply(new BigDecimal(tradeRequest.getQuantity()));
        trader.setBalance(trader.getBalance().add(proceeds));
        traderRepository.save(trader);
        
        // Log the transaction
        transactionRepository.save(new Transaction(trader, stock.getSymbol(), "SELL", tradeRequest.getQuantity(), stock.getPrice()));

        if (holding.getQuantity() == tradeRequest.getQuantity()) {
            holdingRepository.delete(holding);
        } else {
            holding.setQuantity(holding.getQuantity() - tradeRequest.getQuantity());
            holdingRepository.save(holding);
        }
    }
}