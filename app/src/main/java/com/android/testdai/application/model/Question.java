package com.android.testdai.application.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private int mId;
    private String mTextQuestion;
    private List<Answer> mAnswer;
    private String mImageSource;
    private Boolean mAnswered;
    private boolean mSelected;

    public Question(){}

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTextQuestion() {
        return mTextQuestion;
    }

    public void setTextQuestion(String mTextQuestion) {
        this.mTextQuestion = mTextQuestion;
    }

    public List<Answer> getAnswers() {
        return mAnswer;
    }

    public void setAnswers(List<Answer> mAnswer) {
        this.mAnswer = mAnswer;
    }

    public Boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(Boolean mWasAnswered) {
        this.mAnswered = mWasAnswered;
    }

    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String mImageSource) {
        this.mImageSource = mImageSource;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }

    public boolean isImageEmpty(){
        return mImageSource.length() > 2;
    }

    public static class Answer implements Serializable{

        private String mTextAnswer;
        private boolean mAnswerTrue;
        private Boolean mSelected;

        public Answer(){}

        public String getTextAnswer() {
            return mTextAnswer;
        }

        public void setTextAnswer(String mTextAnswer) {
            this.mTextAnswer = mTextAnswer;
        }

        public Boolean isAnswerTrue() {
            return mAnswerTrue;
        }

        public void setAnswerTrue(boolean mAnswerTrue) {
            this.mAnswerTrue = mAnswerTrue;
        }

        public boolean isSelected() {
            return mSelected;
        }

        public void setSelected(Boolean mSelected) {
            this.mSelected = mSelected;
        }
    }

}
