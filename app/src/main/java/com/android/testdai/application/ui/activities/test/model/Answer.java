package com.android.testdai.application.ui.activities.test.model;

public class Answer{

    private String textAnswer;
    private boolean correct;
    private Boolean chosen;
    private Boolean selected;

    public Answer(){}

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String mTextAnswer) {
        this.textAnswer = mTextAnswer;
    }

    public Boolean isAnswerTrue() {
        return correct;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.correct = mAnswerTrue;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(Boolean mChosen) {
        this.chosen = mChosen;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean mSelected) {
        this.selected = mSelected;
    }
}
