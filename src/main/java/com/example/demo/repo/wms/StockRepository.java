package com.example.demo.repo.wms;

import com.example.demo.entity.User;
import com.example.demo.entity.wms.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByUsername(String username);
}
