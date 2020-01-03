package com.android.testdai.ui.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;

import com.android.testdai.application.db.DaiRepository;
import com.android.testdai.model.QuestionEntity;
import com.android.testdai.ui.test.abstraction.ITestView;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class TestPresenter {

    private Context context;
    private List<QuestionEntity> questionEntities;
    private ITestView iTestView;
    private CountDownTimer countdownTimer;
    private int timeLast = 1200000;
    private boolean time = true;
    private boolean timeLimit, errorLimit, doubleClick;
    private boolean testState = true;


    @SuppressLint("CheckResult")
    public TestPresenter(Context context) {

        this.context = context;
//        timeLimit = PreferencesUtil.getInstance(context).getPreference(APP_PREFERENCES_TIME_LIMIT);
//        errorLimit = PreferencesUtil.getInstance(context).getPreference(APP_PREFERENCES_ERROR_LIMIT);
//        doubleClick = PreferencesUtil.getInstance(context).getPreference(APP_PREFERENCES_DOUBLE_CLICK);

        iTestView = (ITestView) context;
        iTestView.startLoading();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
//                databaseRequest(PreferencesUtil.getInstance(context).getCategory());
            }
        }).subscribeOn(Schedulers.io()).andThen(afterRequest()).subscribe();

    }

    public void attachView() {}

    private void databaseRequest(String category) {

        questionEntities = DaiRepository.get(context).getQuestionsList(category);

    }

    private Completable afterRequest() {

        return Completable.fromAction(() -> {
            iTestView.stopLoading();
            if(timeLimit){
                countdownTimer();
            }
            selectQuestion(0);
        }).subscribeOn(AndroidSchedulers.mainThread());

    }


    public void selectQuestion(int position) {

        for (QuestionEntity questionEntity : questionEntities) {
//            questionEntity.setSelected(false);
//            for(QuestionEntity.Answer answer : questionEntity.getAnswers()){
//                answer.setChosen(false);
//            }

        }
//        questionEntities.get(position).setSelected(true);

        iTestView.updateListQuestion(position, questionEntities);

    }

    public void selectAnswer(int position) {

//        QuestionEntity questionEntity = null;
//        for (QuestionEntity q : questionEntities) {
//            if (q.isSelected())
//                questionEntity = q;
//        }
//        assert questionEntity != null;
//        QuestionEntity.Answer answer = questionEntity.getAnswers().get(position);
//        if (!questionEntity.isAnswered() && testState) {
//            if(!answer.isChosen() && doubleClick){
//                for(QuestionEntity.Answer answerl : questionEntity.getAnswers())
//                    answerl.setChosen(false);
//                answer.setChosen(true);
//                iTestView.updateListQuestion(questionEntities.indexOf(questionEntity), questionEntities);
//            }else {
//                questionEntity.setAnswered(true);
//                answer.setSelected(true);
//                answer.setChosen(false);
//                if (testResult()) {
//                    nextQuestion();
//                } else {
//                    iTestView.updateListQuestion(questionEntities.indexOf(questionEntity), questionEntities);
//                }
//            }
//        }


    }

    private void nextQuestion() {

        int pos = 0;

        for (QuestionEntity questionEntity : questionEntities) {
//
//            if (questionEntity.isSelected())
//                pos = questionEntities.indexOf(questionEntity);

        }

        for (int i = 0; i < questionEntities.size(); i++) {

            if (pos >= questionEntities.size() - 1)
                pos = 0;
            else
                pos++;
//            if (!questionEntities.get(pos).isAnswered()) {
//                selectQuestion(pos);
//                break;
//            }

        }

    }

    private boolean testResult() {

        int questionCount = 0;
        int answerIsTrueCount = 0;
        int answerIsFalseCount = 0;

//        List<QuestionEntity.Answer> answers;

//        for (QuestionEntity questionEntity : questionEntities) {
//            if (questionEntity.isAnswered()) {
//                questionCount++;
//            }
//            answers = questionEntity.getAnswers();
//            for (QuestionEntity.Answer answer : answers) {
//                if (answer.isAnswerTrue() && answer.isSelected()) {
//                    answerIsTrueCount++;
//                }
//                if (!answer.isAnswerTrue() && answer.isSelected()) {
//                    answerIsFalseCount++;
//                }
//            }
//        }

        if (questionCount == questionEntities.size() || timerState() || isErrorLimit(answerIsFalseCount) ) {
            stopCountdownTimer();
            testState = false;
            iTestView.showResultDialog(answerIsTrueCount);
            return false;
        }

        return true;

    }

    private void countdownTimer() {

        countdownTimer = new CountDownTimer(timeLast, 1000) {

            public void onTick(long millisUntilFinished) {
                if (!time) {
                    countdownTimer.cancel();
                    iTestView.updateTimer(timeLast);
                } else {
                    timeLast = (int) millisUntilFinished;
                    iTestView.updateTimer((int) millisUntilFinished);
                }
            }

            public void onFinish() {
                iTestView.updateTimer(0);
                time = false;
                testState = false;
                testResult();
            }

        }.start();
    }

    private boolean timerState(){

        if (timeLimit)
            return false;
        else return !time;

    }

    private boolean isErrorLimit(int answerIsFalseCount){

        if(errorLimit && answerIsFalseCount>2)
            return true;
        else return false;

    }

    private void stopCountdownTimer(){
        if(timeLimit){
            countdownTimer.cancel();
        }
    }

    public void onDestroy() {
        stopCountdownTimer();
    }
}
