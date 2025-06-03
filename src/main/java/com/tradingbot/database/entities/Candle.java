package com.tradingbot.database.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Candle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time; 
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private BigDecimal volume;
    private BigDecimal wap;  
    private Integer count;   
    private String symbol;
    private String barSize;
    private String whatToShow;
    private Boolean useRTH;
    private Integer formatDate;
    private String exchange;
    private String currency;
    private String secType;
    private LocalDateTime lastUpdateTime;
    private Boolean isRealTime;

    public Candle() {}
    
    public Candle(String time, Double open, Double high, Double low, Double close, 
                  BigDecimal volume, BigDecimal wap, Integer count, String symbol, String barSize) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.wap = wap;
        this.count = count;
        this.symbol = symbol;
        this.barSize = barSize;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public Double getOpen() {
        return open;
    }
    
    public void setOpen(Double open) {
        this.open = open;
    }
    
    public Double getHigh() {
        return high;
    }
    
    public void setHigh(Double high) {
        this.high = high;
    }
    
    public Double getLow() {
        return low;
    }
    
    public void setLow(Double low) {
        this.low = low;
    }
    
    public Double getClose() {
        return close;
    }
    
    public void setClose(Double close) {
        this.close = close;
    }
    
    public BigDecimal getVolume() {
        return volume;
    }
    
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    
    public BigDecimal getWap() {
        return wap;
    }
    
    public void setWap(BigDecimal wap) {
        this.wap = wap;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public String getBarSize() {
        return barSize;
    }
    
    public void setBarSize(String barSize) {
        this.barSize = barSize;
    }
    
    public String getWhatToShow() {
        return whatToShow;
    }
    
    public void setWhatToShow(String whatToShow) {
        this.whatToShow = whatToShow;
    }
    
    public Boolean getUseRTH() {
        return useRTH;
    }
    
    public void setUseRTH(Boolean useRTH) {
        this.useRTH = useRTH;
    }
    
    public Integer getFormatDate() {
        return formatDate;
    }
    
    public void setFormatDate(Integer formatDate) {
        this.formatDate = formatDate;
    }
    
    public String getExchange() {
        return exchange;
    }
    
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getSecType() {
        return secType;
    }
    
    public void setSecType(String secType) {
        this.secType = secType;
    }
    
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public Boolean getIsRealTime() {
        return isRealTime;
    }
    
    public void setIsRealTime(Boolean isRealTime) {
        this.isRealTime = isRealTime;
    }
    
    @Override
    public String toString() {
        return "Candle{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", symbol='" + symbol + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", wap=" + wap +
                ", count=" + count +
                ", barSize='" + barSize + '\'' +
                '}';
    }
}
