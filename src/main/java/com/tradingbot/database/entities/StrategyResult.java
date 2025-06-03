package com.tradingbot.database.entities;

import com.tradingbot.database.enums.Signal;

public class StrategyResult {

    private Signal signal;
    private Trade recommendedTrade;
    private double confidence; //between 0 and 1
    private String explanation; //might use this for storing strategy used if reults get stored in db
}