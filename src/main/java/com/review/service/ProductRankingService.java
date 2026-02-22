package com.review.service;

import com.review.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductRankingService {

    // Sentiment → Score mapping
    private int getSentimentScore(String sentiment) {
        if (sentiment == null) return 0;
        switch (sentiment.trim().toLowerCase()) {
            case "very positive": return 2;
            case "positive": return 1;
            case "neutral": return 0;
            case "negative": return -1;
            case "very negative": return -2;
            default: return 0;
        }
    }

    public void generateReport() {
        String sql = "SELECT p.id, p.name, p.description, p.price, r.sentiment " +
                     "FROM products p LEFT JOIN reviews r ON p.id = r.product_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n📊 Product Ranking Report:");

            // Track scores per product
            java.util.Map<Integer, ProductScore> productScores = new java.util.HashMap<>();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
                String sentiment = rs.getString("sentiment");

                productScores.putIfAbsent(productId, new ProductScore(name, desc, price));
                productScores.get(productId).score += getSentimentScore(sentiment);
            }

            // Sort by score (descending)
            productScores.values().stream()
                    .sorted((a, b) -> Integer.compare(b.score, a.score))
                    .forEach(p -> System.out.println(
                            p.name + " | Price: " + p.price + " | Rank Score: " + p.score));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper class
    static class ProductScore {
        String name;
        String desc;
        double price;
        int score = 0;

        ProductScore(String name, String desc, double price) {
            this.name = name;
            this.desc = desc;
            this.price = price;
        }
    }
}
