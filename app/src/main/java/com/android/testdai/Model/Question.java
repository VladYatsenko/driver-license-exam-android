package com.android.testdai.Model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private int mId;
    private String mTextQuestion;
    private List<Answer> mAnswer;
    private String mImageSource;
    private Bitmap mImage;
    private Boolean mWasAnswered;
    private boolean mWasSelected;

    public Question(){}

    public List<Answer> getAnswer() {
        return mAnswer;
    }

    public void setAnswer(List<Answer> mAnswer) {
        this.mAnswer = mAnswer;
    }

    public String getTextQuestion() {
        return mTextQuestion;
    }

    public void setTextQuestion(String mTextQuestion) {
        this.mTextQuestion = mTextQuestion;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public Boolean isWasAnswered() {
        return mWasAnswered;
    }

    public void setWasAnswered(Boolean mWasAnswered) {
        this.mWasAnswered = mWasAnswered;
    }

    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String mImageSource) {
        this.mImageSource = mImageSource;
    }

    public boolean isWasSelected() {
        return mWasSelected;
    }

    public void setWasSelected(boolean mWasSelected) {
        this.mWasSelected = mWasSelected;
    }

    public boolean isImageEmpty(){
        if (mImageSource.length()>2){
            return false;
        }
        return true;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getPhotoFilename() {
        return "IMG_" + String.valueOf(getId()) + ".png";
    }

    public static class Answer implements Serializable{

        private String mTextAnswer;
        private boolean mAnswerTrue;
        private Boolean mWasChoosen;

        public String getTextAnswer() {
            return mTextAnswer;
        }

        public void setTextAnswer(String mTextAnswer) {
            this.mTextAnswer = mTextAnswer;
        }

        public Answer(){}

        public Boolean isAnswerTrue() {
            return mAnswerTrue;
        }

        public void setAnswerTrue(boolean mAnswerTrue) {
            this.mAnswerTrue = mAnswerTrue;
        }

        public boolean isWasChoosen() {
            return mWasChoosen;
        }

        public void setWasChoosen(Boolean mWasChoosed) {
            this.mWasChoosen = mWasChoosed;
        }
    }
}
