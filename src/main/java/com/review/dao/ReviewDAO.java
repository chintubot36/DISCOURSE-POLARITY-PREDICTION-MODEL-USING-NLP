package com.review.dao;

import com.review.db.DBConnection;
import com.shoppingcartnlp.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Add review
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (product_id, review_text, sentiment) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getProductId());
            stmt.setString(2, review.getReviewText());   // ✅ fixed
            stmt.setString(3, review.getSentiment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all reviews for a product
    public List<Review> getReviewsByProduct(int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setProductId(rs.getInt("product_id"));
                    review.setReviewText(rs.getString("review_text")); // ✅ fixed
                    review.setSentiment(rs.getString("sentiment"));
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
