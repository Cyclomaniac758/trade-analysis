package com.tradingbot.database.Repositories;

import com.tradingbot.database.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
