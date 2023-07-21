package com.ritacsullag.reviewerplugin.model;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ReviewerList {
    private static final Logger logger = Logger.getInstance(ReviewerList.class);
    public static final String USERHOME = System.getProperty("user.home");

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
            createErrorMessagePopUp();
        }
    }

    private static void createErrorMessagePopUp() {
        String message = "Something went wrong, we could not save the new Reviewer. Please try it again!";
        JPanel panel = new JPanel();
        panel.setBackground(MessageType.ERROR.getPopupBackground());
        panel.add(new JLabel(message), BorderLayout.WEST);
        panel.add(new JLabel(MessageType.ERROR.getDefaultIcon()), BorderLayout.EAST);

        ComponentPopupBuilder builder = JBPopupFactory.getInstance().createComponentPopupBuilder(panel, null)
                .setCancelOnClickOutside(true)
                .setCancelKeyEnabled(true)
                .setRequestFocus(true);
        builder.createPopup().showInFocusCenter();
    }
}
