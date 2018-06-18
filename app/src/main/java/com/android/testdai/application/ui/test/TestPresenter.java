package com.android.testdai.application.ui.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;

import com.android.testdai.application.db.DaiRepository;
import com.android.testdai.application.model.Question;
import com.android.testdai.application.ui.test.abstraction.ITestView;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class TestPresenter {

    private Context context;
    private List<Question> questions;
    private ITestView iTestView;
    private CountDownTimer countdownTimer;
    private int timeLast = 1200000;
    private boolean time = true;
    private boolean testState = true;


    @SuppressLint("CheckResult")
    TestPresenter(Context context, String category) {

        this.context = context;
        iTestView = (ITestView) context;
        iTestView.startLoading();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                databaseRequest(category);
            }
        }).subscribeOn(Schedulers.io()).andThen(afterRequest()).subscribe();

    }

    public void attachView() {}

    private void databaseRequest(String category) {

        questions = DaiRepository.get(context).getQuestionsList(category);

    }

    private Completable afterRequest() {

        return Completable.fromAction(() -> {
            iTestView.stopLoading();
            countdownTimer();
            selectQuestion(0);
        }).subscribeOn(AndroidSchedulers.mainThread());

    }


    public void selectQuestion(int position) {

        for (Question question : questions) {
            question.setSelected(false);
        }
        questions.get(position).setSelected(true);

        iTestView.updateListQuestion(position, questions);

    }

    public void selectAnswer(int position) {

        Question question = null;
        for (Question q : questions) {
            if (q.isSelected())
                question = q;
        }
        assert question != null;
        Question.Answer answer = question.getAnswers().get(position);
        if (!question.isAnswered() && testState) {
            question.setAnswered(true);
            answer.setSelected(true);
            if (testResult()) {
                nextQuestion();
            } else {
                iTestView.updateListQuestion(questions.indexOf(question), questions);
            }
        }


    }

    private void nextQuestion() {

        int pos = 0;

        for (Question question : questions) {

            if (question.isSelected())
                pos = questions.indexOf(question);

        }

        for (int i = 0; i < questions.size(); i++) {

            if (pos >= questions.size() - 1)
                pos = 0;
            else
                pos++;
            if (!questions.get(pos).isAnswered()) {
                selectQuestion(pos);
                break;
            }

        }

    }

    private boolean testResult() {

        int questionCount = 0;
        int answerIsTrueCount = 0;
        int answerIsFalseCount = 0;

        List<Question.Answer> answers;

        for (Question question : questions) {
            if (question.isAnswered()) {
                questionCount++;
            }
            answers = question.getAnswers();
            for (Question.Answer answer : answers) {
                if (answer.isAnswerTrue() && answer.isSelected()) {
                    answerIsTrueCount++;
                }
                if (!answer.isAnswerTrue() && answer.isSelected()) {
                    answerIsFalseCount++;
                }
            }
        }
        if (questionCount == questions.size() || !time || answerIsFalseCount > 2) {
            countdownTimer.cancel();
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

    public void onDestroy() {
        countdownTimer.cancel();
    }
}
