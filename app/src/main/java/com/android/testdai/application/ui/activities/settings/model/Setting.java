package com.android.testdai.application.ui.activities.settings.model;

public class Setting {

    private String header;
    private String description;
    private boolean checked;

    public Setting(String header, String description) {
        this.header = header;
        this.description = description;
        this.checked = false;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }



}
