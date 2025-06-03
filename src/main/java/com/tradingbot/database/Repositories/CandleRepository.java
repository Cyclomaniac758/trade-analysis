package com.tradingbot.database.Repositories;

import com.tradingbot.database.entities.Candle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandleRepository extends JpaRepository<Candle, Long> {
    
    List<Candle> findBySymbolOrderByTimeAsc(String symbol);
    
    List<Candle> findBySymbolAndBarSizeOrderByTimeAsc(String symbol, String barSize);
    
    @Query("SELECT c FROM Candle c WHERE c.symbol = :symbol AND c.barSize = :barSize AND c.whatToShow = :whatToShow ORDER BY c.time ASC")
    List<Candle> findBySymbolAndBarSizeAndWhatToShowOrderByTimeAsc(
        @Param("symbol") String symbol, 
        @Param("barSize") String barSize, 
        @Param("whatToShow") String whatToShow
    );
}
