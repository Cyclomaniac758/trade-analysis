package com.tradingbot;

import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tradeinfo";
        String user = "postgres";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed:");
            e.printStackTrace();
        }
    }
}

