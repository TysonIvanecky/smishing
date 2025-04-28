package com.example.smishingdetectionapp;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SmishingDetector {

    // Regular expressions for detecting suspicious patterns
    private static final String CLICK_PATTERN = "(?i)(click|claim|urgent|verify|action\\s+required)"; // Case-insensitive
    private static final String URL_PATTERN = "(?i)http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+"; // Matches URLs
    private static final String SMISHING_KEYWORDS_PATTERN = "(?i)(win|congratulations|free|prize|won|gift|cash|overdue\\s+payment|toll\\s+payment|payment\\s+failure)"; // Smishing-related keywords

    public static int getSmishingSeverity(String message) {
        int severity = 0;

        // Check for suspicious keywords
        int keywordCount = countMatches(SMISHING_KEYWORDS_PATTERN, message);
        if (keywordCount > 0) {
            severity += (keywordCount >= 2) ? 2 : 1; // 1 for one keyword, 2 for multiple
        }

        // Check for suspicious action phrases
        if (containsPattern(CLICK_PATTERN, message)) {
            severity += 1;
        }

        // Check for suspicious URLs
        if (containsPattern(URL_PATTERN, message)) {
            severity += 2;
        }

        // Cap severity at 3 (High Risk)
        return Math.min(severity, 3);
    }

    // Count occurrences of regex matches in a message
    private static int countMatches(String regex, String message) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Check if a message contains a pattern at least once
    private static boolean containsPattern(String regex, String message) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }

    // Main method for testing
    public static void main(String[] args) {
        // Sample messages for testing
        String[] testMessages = {
                "Congratulations! You've won a free gift! Click now to claim: http://fake-prize.com",
                "Your payment is overdue, click here to resolve: http://payment-failure.com",
                "Verify your account now: https://example.com",
                "Your order has shipped, track it here: https://legitimate-link.com",
                "Important update, click here to claim your prize!",
                "Reminder: Your toll payment is overdue.",
                "Win big prizes now! Free cash just for you!"
        };

        // Check each message
        for (String message : testMessages) {
            int severity = getSmishingSeverity(message);
            System.out.println("Message: \"" + message + "\"");
            System.out.println("Smishing Severity: " + severity + " (1=Low, 2=Medium, 3=High)\n");
        }
    }
}
