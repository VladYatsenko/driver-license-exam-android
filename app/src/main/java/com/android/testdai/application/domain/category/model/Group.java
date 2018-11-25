package com.android.testdai.application.domain.category.model;

public class Group {

    private GroupEnum groupName;
    private boolean enabled;


    public Group(GroupEnum groupName) {
        this.groupName = groupName;
        this.enabled = false;
    }

    public GroupEnum getGroupName() {
        return groupName;
    }

    public void setGroupName(GroupEnum group) {
        this.groupName = group;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
