package com.android.testdai.application.domain.category.model;

public class Category {

    private String name;
    private boolean selected;
    private Group group;

    public Category(String name, Group group) {

        this.name = name;
        this.selected = false;
        this.group = group;

    }

    public String getName() {
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
