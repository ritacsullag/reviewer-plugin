package com.ritacsullag.reviewerplugin;

import java.util.Arrays;
import java.util.List;

public class ReviewMessageCreator {

    private static final String lineSep = System.lineSeparator();

    static String addReviewers(String baseMessage, List<String> reviewers) {
        String reviewerLines = reviewers.stream()
                .map(reviewer -> "Review: @" + getReviewerUsername(reviewer))
                .reduce((acc, line) -> acc + lineSep + line)
                .orElse("");


        String newMessage = removePreviousReviewers(baseMessage).trim() +
                lineSep + lineSep + lineSep +
                reviewerLines;
        return newMessage.trim();
    }

    private static String getReviewerUsername(String reviewer){
        int index = reviewer.indexOf("<");
        return reviewer.substring(0, index).trim();
    }

    private static String removePreviousReviewers(String message) {
        return Arrays.stream(message.split(System.lineSeparator()))
                .filter(line -> !line.startsWith("Review: @"))
                .reduce((acc, line) -> acc + lineSep + line)
                .orElse("");
    }

}

