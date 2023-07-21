package com.ritacsullag.reviewerplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ui.Refreshable;
import com.ritacsullag.reviewerplugin.model.ReviewerList;
import com.ritacsullag.reviewerplugin.model.ReviewerSelector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReviewerButton extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        List<String> reviewers = ReviewerList.getReviewers();
        ReviewerSelector reviewerSelector = new ReviewerSelector(true, reviewers);
        reviewerSelector.showAndGet();

        List<String> modifiedReviewers = reviewerSelector.getAllReviewers();
        if (!modifiedReviewers.equals(reviewers)) {
            ReviewerList.saveReviewers(modifiedReviewers);
        }

        if (reviewerSelector.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            CheckinProjectPanel data = (CheckinProjectPanel) Refreshable.PANEL_KEY.getData(event.getDataContext());
            updateCommitMessage(data, reviewerSelector.getSelectedReviewers());
        }
    }

    private void updateCommitMessage(CheckinProjectPanel checkinPanel, List<String> reviewers) {
        String message = ReviewMessageCreator.addReviewers(
                checkinPanel.getCommitMessage(),
                reviewers
        );
        checkinPanel.setCommitMessage(message);
    }
}

