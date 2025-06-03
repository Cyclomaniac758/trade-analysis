package com.tradingbot.database.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BacktestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String strategyName;

    private LocalDateTime runDate;

    private double totalReturn;

    private double maxDrawdown;

    private double sharpeRatio;

    @Column(length = 2048)
    private String parametersJson; // store strategy parameters as JSON string
    
    private String symbol;
    
    private String barSize;
    
    private String whatToShow;
    
    private String durationString;
    
    private Boolean useRTH;
    
    private String exchange;
    
    private String currency;
    
    private String secType;
    
    private LocalDateTime dataStartTime;
    
    private LocalDateTime dataEndTime;
    
    private Integer totalCandles;

    // Constructors, getters and setters

    public BacktestResult() {}

    public BacktestResult(String strategyName, LocalDateTime runDate, double totalReturn, double maxDrawdown, double sharpeRatio, String parametersJson) {
        this.strategyName = strategyName;
        this.runDate = runDate;
        this.totalReturn = totalReturn;
        this.maxDrawdown = maxDrawdown;
        this.sharpeRatio = sharpeRatio;
        this.parametersJson = parametersJson;
    }
    
    public BacktestResult(String strategyName, String symbol, String barSize, String whatToShow, 
                         String durationString, Boolean useRTH, String parametersJson) {
        this.strategyName = strategyName;
        this.symbol = symbol;
        this.barSize = barSize;
        this.whatToShow = whatToShow;
        this.durationString = durationString;
        this.useRTH = useRTH;
        this.parametersJson = parametersJson;
        this.runDate = LocalDateTime.now();
        this.exchange = "SMART";
        this.currency = "USD";
        this.secType = "STK";
    }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getBarSize() { return barSize; }
    public void setBarSize(String barSize) { this.barSize = barSize; }
    
    public String getWhatToShow() { return whatToShow; }
    public void setWhatToShow(String whatToShow) { this.whatToShow = whatToShow; }
    
    public String getDurationString() { return durationString; }
    public void setDurationString(String durationString) { this.durationString = durationString; }
    
    public Boolean getUseRTH() { return useRTH; }
    public void setUseRTH(Boolean useRTH) { this.useRTH = useRTH; }
    
    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getSecType() { return secType; }
    public void setSecType(String secType) { this.secType = secType; }
    
    public LocalDateTime getDataStartTime() { return dataStartTime; }
    public void setDataStartTime(LocalDateTime dataStartTime) { this.dataStartTime = dataStartTime; }
    
    public LocalDateTime getDataEndTime() { return dataEndTime; }
    public void setDataEndTime(LocalDateTime dataEndTime) { this.dataEndTime = dataEndTime; }
    
    public Integer getTotalCandles() { return totalCandles; }
    public void setTotalCandles(Integer totalCandles) { this.totalCandles = totalCandles; }
    
    // Existing getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStrategyName() { return strategyName; }
    public void setStrategyName(String strategyName) { this.strategyName = strategyName; }
    
    public LocalDateTime getRunDate() { return runDate; }
    public void setRunDate(LocalDateTime runDate) { this.runDate = runDate; }
    
    public double getTotalReturn() { return totalReturn; }
    public void setTotalReturn(double totalReturn) { this.totalReturn = totalReturn; }
    
    public double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    
    public double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    
    public String getParametersJson() { return parametersJson; }
    public void setParametersJson(String parametersJson) { this.parametersJson = parametersJson; }

    // getters/setters omitted for brevity
}
