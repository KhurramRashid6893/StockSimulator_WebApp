// In src/main/java/com/stock/simulator/repository/TraderRepository.java

package com.stock.simulator.repository;

import com.stock.simulator.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {
    Optional<Trader> findByName(String name);
}