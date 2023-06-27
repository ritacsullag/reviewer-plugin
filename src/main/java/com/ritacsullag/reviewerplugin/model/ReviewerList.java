package com.ritacsullag.reviewerplugin.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ReviewerList {
    private static final Logger logger = Logger.getInstance(ReviewerList.class);
    public static final String USERHOME = System.getProperty("user.home");

    ReviewerList() {
    }

    public static List<String> getReviewers() {

        try {
            return Files.readAllLines(getPathForReviewerList());
        } catch (IOException e) {
            logger.error(e);
            return Collections.emptyList();
        }
    }

    @NotNull
    private static Path getPathForReviewerList() {
        Path coAuthorListPath = Path.of(USERHOME, ".git_coauthors");
        return Files.exists(coAuthorListPath) ? coAuthorListPath : Path.of(USERHOME, ".reviewers");
    }

    public static void saveReviewers(List<String> reviewers) {
        try {
            Files.write(getPathForReviewerList(), reviewers);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
