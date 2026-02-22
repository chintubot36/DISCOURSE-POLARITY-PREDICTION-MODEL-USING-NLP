package com.review.main;

import com.review.dao.ProductDAO;
import com.review.dao.ReviewDAO;
import com.shoppingcartnlp.model.Product;
import com.shoppingcartnlp.model.Review;
import com.review.nlp.NLPSentimentAnalyzer;
import com.review.service.*;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        Scanner scanner = new Scanner(System.in);

        // Show products
        List<Product> products = productDAO.getAllProducts();
        System.out.println("Available Products:");
        for (Product p : products) {
            System.out.println("- " + p.getName() + " (Rs." + p.getPrice() + ")");
        }

        // Customer inputs product name
        System.out.print("\nEnter product name to review: ");
        String productName = scanner.nextLine();

        Product product = productDAO.getProductByName(productName);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }

        // Customer writes review
        System.out.print("Enter your review: ");
        String reviewText = scanner.nextLine();

        // NLP Sentiment analysis
        String sentiment = NLPSentimentAnalyzer.analyzeSentiment(reviewText);

        // Save review
        Review review = new Review(product.getId(), reviewText, sentiment);
        reviewDAO.addReview(review);

        System.out.println("✅ Review saved for " + product.getName() + " with sentiment: " + sentiment);
        
     // Generate product ranking report
        ProductRankingService rankingService = new ProductRankingService();
        rankingService.generateReport();
        scanner.close();

    }
}
