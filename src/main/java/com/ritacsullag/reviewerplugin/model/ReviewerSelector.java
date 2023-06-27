package com.ritacsullag.reviewerplugin.model;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ReviewerSelector extends DialogWrapper {

    ReviewerTable reviewerTable;

    public ReviewerSelector(boolean canBeParent, List<String> reviewers) {
        super(canBeParent);

        this.reviewerTable = new ReviewerTable(reviewers);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel mainDiaglog = new JPanel(new BorderLayout());


        JBTable table = new JBTable(reviewerTable);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table);
        mainDiaglog.add(table, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        ActionToolbar actionToolbar = createActionBar(table);
        actionPanel.add(actionToolbar.getComponent());
        mainDiaglog.add(actionPanel, BorderLayout.AFTER_LAST_LINE);

        return mainDiaglog;
    }

    private ActionToolbar createActionBar(JBTable table) {
        DefaultActionGroup actionBarGroup = new DefaultActionGroup();

        actionBarGroup.addAction(new AnAction("Add", "Add new Reviewer", AllIcons.General.Add) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                reviewerTable.addNewReviewerRow();
            }
        });

        actionBarGroup.addAction(new AnAction("Remove", "Remove a Reviewer", AllIcons.General.Remove) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                reviewerTable.removeReviewerRow(table.getSelectedRows());
            }
        });
        return ActionManager.getInstance().createActionToolbar("MyToolBar", actionBarGroup, true);
    }


    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public List<String> getAllReviewers() {
        return reviewerTable.getAllReviewers();
    }

    public List<String> getSelectedReviewers() {
        return reviewerTable.getSelectedRviewers();
    }
}
