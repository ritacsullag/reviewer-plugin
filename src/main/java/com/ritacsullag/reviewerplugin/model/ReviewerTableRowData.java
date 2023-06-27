package com.ritacsullag.reviewerplugin.model;

public class ReviewerTableRowData {

    private Boolean isSelected;
    private String reviewer;

    public ReviewerTableRowData(Boolean isSelected, String reviewer) {
        this.isSelected = isSelected;
        this.reviewer = reviewer;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
