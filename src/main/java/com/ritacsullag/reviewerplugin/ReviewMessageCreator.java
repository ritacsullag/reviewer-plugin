package com.ritacsullag.reviewerplugin;

import java.util.Arrays;
import java.util.List;

public class ReviewMessageCreator {

    private static final String lineSep = System.lineSeparator();

    static String addReviewers(String baseMessage, List<String> reviewers) {
        String reviewerLines = reviewers.stream()
                .map(reviewer -> "Review: " + reviewer)
                .reduce((acc, line) -> acc + lineSep + line)
                .orElse("");


        String newMessage = removePreviousReviewers(baseMessage).trim() +
                lineSep + lineSep + lineSep +
                reviewerLines;
        return newMessage.trim();
    }

    private static String removePreviousReviewers(String message) {
        return Arrays.stream(message.split(System.lineSeparator()))
                .filter(line -> !line.startsWith("Review:"))
                .reduce((acc, line) -> acc + lineSep + line)
                .orElse("");
    }

}

