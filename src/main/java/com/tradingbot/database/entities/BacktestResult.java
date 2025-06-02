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

    // getters/setters omitted for brevity
}
