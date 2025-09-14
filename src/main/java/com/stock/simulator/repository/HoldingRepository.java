package com.stock.simulator.repository;

import com.stock.simulator.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByTraderId(Long traderId);
    
    // Spring Data JPA query to find a specific holding by trader and stock
    Optional<Holding> findByTraderIdAndStockSymbol(Long traderId, String stockSymbol);
}