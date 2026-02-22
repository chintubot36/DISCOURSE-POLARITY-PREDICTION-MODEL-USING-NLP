package com.shoppingcartnlp.model;

public class Review {
    private int id;
    private int productId;
    private String reviewText;
    private String sentiment;

    public Review() {}

    public Review(int productId, String reviewText, String sentiment) {
        this.productId = productId;
        this.reviewText = reviewText;
        this.sentiment = sentiment;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
}

