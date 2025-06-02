package com.tradingbot.database.Repositories;

import com.tradingbot.database.entities.BacktestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacktestResultRepository extends JpaRepository<BacktestResult, Long> {
}
