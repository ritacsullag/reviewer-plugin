package com.ritacsullag.reviewerplugin;

import java.util.Arrays;
import java.util.List;

public class ReviewMessageCreator {

    static String addReviewers(String baseMessage, List<String> reviewers) {
        String reviewerLines = reviewers.stream()
                .map(reviewer -> "Review: " + reviewer)
                .reduce((acc, line) -> acc + "\n" + line)
                .orElse("");


        String newMessage = removePreviousReviewers(baseMessage).trim() +
                "\n\n\n" +
                reviewerLines;
        return newMessage.trim();
    }


    private static String removePreviousReviewers(String message) {
        return Arrays.stream(message.split("\n"))
                .filter(line -> !line.startsWith("Review:"))
                .reduce((acc, line) -> acc + "\n" + line)
                .orElse("");
    }

}

