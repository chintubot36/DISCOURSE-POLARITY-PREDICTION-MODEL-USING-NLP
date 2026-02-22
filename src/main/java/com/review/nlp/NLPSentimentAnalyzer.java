package com.review.nlp;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import java.util.Properties;

public class NLPSentimentAnalyzer {

    private static StanfordCoreNLP pipeline;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static String analyzeSentiment(String text) {
        if (text == null || text.isEmpty()) return "Neutral";

        String lower = text.toLowerCase();

        // ✅ Strongly Negative words
        if (lower.contains("very bad") || lower.contains("very very bad")
                || lower.contains("worst") || lower.contains("hate")
                || lower.contains("terrible") || lower.contains("awful")) {
            return "Very Negative";
        }

        // ✅ Negative words
        if (lower.contains("not like") || lower.contains("dont like")
                || lower.contains("bad") || lower.contains("poor")
                || lower.contains("disappointed")) {
            return "Negative";
        }

        // ✅ Strongly Positive words
        if (lower.contains("excellent") || lower.contains("awesome")
                || lower.contains("amazing") || lower.contains("love it")
                || lower.contains("best")) {
            return "Very Positive";
        }

        // ✅ Positive words
        if (lower.contains("good") || lower.contains("like")) {
            return "Positive";
        }

     // Strong Positive
        if (lower.contains("very good") || lower.contains("very very good")
                || lower.contains("excellent") || lower.contains("awesome")
                || lower.contains("amazing") || lower.contains("love it")
                || lower.contains("best")) {
            return "Very Positive";
        }

        // ✅ Default: Use CoreNLP sentiment
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        int totalScore = 0;
        int count = 0;

        for (CoreMap sentence : document.annotation().get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            totalScore += mapSentimentToScore(sentiment);
            count++;
        }

        if (count == 0) return "Neutral";

        int avgScore = Math.round((float) totalScore / count);
        return mapScoreToSentiment(avgScore);
    }

    // Map CoreNLP sentiment text → numeric
    private static int mapSentimentToScore(String sentiment) {
        switch (sentiment) {
            case "Very Negative": return -2;
            case "Negative": return -1;
            case "Positive": return 1;
            case "Very Positive": return 2;
            default: return 0;
        }
    }

    // Map numeric → final sentiment text
    private static String mapScoreToSentiment(int score) {
        switch (score) {
            case -2: return "Very Negative";
            case -1: return "Negative";
            case 1:  return "Positive";
            case 2:  return "Very Positive";
            default: return "Neutral";
        }
    }
}
