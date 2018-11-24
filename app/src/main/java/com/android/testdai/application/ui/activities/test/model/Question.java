package com.android.testdai.application.ui.activities.test.model;

import java.util.List;

public class Question{

    private int id;
    private String textQuestion;
    private List<Answer> answers;
    private String imageSource;
    private Boolean answered;
    private boolean selected;

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String mTextQuestion) {
        this.textQuestion = mTextQuestion;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> mAnswer) {
        this.answers = mAnswer;
    }

    public Boolean isAnswered() {
        return answered;
    }

    public void setAnswered(Boolean mWasAnswered) {
        this.answered = mWasAnswered;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String mImageSource) {
        this.imageSource = mImageSource;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean mSelected) {
        this.selected = mSelected;
    }

    public boolean isImageEmpty(){
        return imageSource.length() > 2;
    }



}
