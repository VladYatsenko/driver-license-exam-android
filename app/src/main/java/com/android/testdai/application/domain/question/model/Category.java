package com.android.testdai.application.domain.question.model;

public class Category {

    private int name;
    private boolean selected;
    private Group group;

    public Category(int name, Group group) {

        this.name = name;
        this.selected = false;
        this.group = group;

    }

    public int getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Group getGroup() {
        return group;
    }


}
