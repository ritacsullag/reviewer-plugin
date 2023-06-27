package com.ritacsullag.reviewerplugin.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewerTable extends AbstractTableModel {
    private List<ReviewerTableRowData> reviewersForTable;

    public ReviewerTable(List<String> reviewers) {
        this.reviewersForTable = reviewers.stream()
                .map(reviewer -> new ReviewerTableRowData(false, reviewer))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return reviewersForTable.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReviewerTableRowData reviewerTableRowData = reviewersForTable.get(rowIndex);
        return columnIndex == 0 ? reviewerTableRowData.getSelected() : reviewerTableRowData.getReviewer();
    }


    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            reviewersForTable.get(rowIndex).setSelected((boolean) value);
        }
        if (columnIndex == 1) {
            reviewersForTable.get(rowIndex).setReviewer((String) value);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public void addNewReviewerRow() {
        reviewersForTable.add(new ReviewerTableRowData(false, ""));
        fireTableDataChanged();
    }

    public void removeReviewerRow(int[] selectedRowIndex) {
        for (int i = selectedRowIndex.length - 1; i >= 0; i--) {
            reviewersForTable.remove(selectedRowIndex[i]);
        }
        fireTableDataChanged();
    }

    public List<String> getAllReviewers() {
        return reviewersForTable.stream()
                .map(ReviewerTableRowData::getReviewer)
                .collect(Collectors.toList());
    }

    public List<String> getSelectedRviewers() {
        return reviewersForTable.stream()
                .filter(ReviewerTableRowData::getSelected)
                .map(ReviewerTableRowData::getReviewer)
                .collect(Collectors.toList());
    }
}
