package com.android.testdai.application.ui.test;

import android.content.Context;
import android.os.CountDownTimer;

import com.android.testdai.application.db.DaiRepository;
import com.android.testdai.application.model.Question;
import com.android.testdai.application.model.QuestionLab;
import com.android.testdai.application.ui.test.abstraction.ITestView;
import com.android.testdai.application.ui.test.model.AnswerItemFactory;
import com.android.testdai.application.ui.test.model.Item;
import com.android.testdai.application.ui.test.model.ItemFactory;
import com.android.testdai.application.ui.test.model.QuestionItemFactory;

import java.util.List;

public class TestPresenter {

    private Context context;
    private List<Question> questions;
    private ITestView iTestView;
    private int questionPosition;
    private CountDownTimer mCountDownTimer;
    private int mTimerLast = 1200000;
    boolean mTime = true;

    private boolean state;

    public TestPresenter (Context context, String category){

        this.context = context;
        iTestView = (ITestView) context;
        iTestView.startLoading();
        databaseRequest(category);

    }

    public void attachView() {

        iTestView.updateListQuestion(0, questions);
        selectQuestion(0);
        iTestView.stopLoading();
        countdownTimer();
        state = true;

    }

    private void databaseRequest(String category) {

        //questions = QuestionLab.get(context).getQuestions(category);
        questions = DaiRepository.get(context).getQuestionsList(category);

    }

    public int drawableSelector(Object itemClass) {

        ItemFactory itemFactory = null;

        if (itemClass.getClass().equals(Question.class))
            itemFactory = new QuestionItemFactory();
        else if (itemClass.getClass().equals(Question.Answer.class))
            itemFactory = new AnswerItemFactory();

        assert itemFactory != null;
        Item item = itemFactory.createItem();

        return item.selectDrawable(itemClass);

    }

    public void selectQuestion(int position) {

        for (Question question : questions) {
            question.setSelected(false);
        }
        questions.get(position).setSelected(true);
        //question.setSelected(true);

        iTestView.updateListQuestion(position, questions);
        this.questionPosition = position;

    }

    public void selectAnswer(int position) {

        Question question = questions.get(questionPosition);
        Question.Answer answer = question.getAnswers().get(position);
        if (!question.isAnswered()) {
            if (!question.isAnswered()) {
                question.setAnswered(true);
                answer.setSelected(true);
                if (testResult()) {
                    nextQuestion();
                } else {
                    iTestView.updateListQuestion(questions.indexOf(question), questions);
                }
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
        if (questionCount == questions.size() || !mTime || answerIsFalseCount > 2) {
            state = true;
            mCountDownTimer.cancel();

            iTestView.showResultDialog(answerIsTrueCount);
            return false;
        }
        return true;

    }

    private void countdownTimer() {

        mCountDownTimer = new CountDownTimer(mTimerLast, 1000) {

            public void onTick(long millisUntilFinished) {
                if (!mTime) {
                    mCountDownTimer.cancel();
                    iTestView.updateTimer(mTimerLast);
                } else {
                    mTimerLast = (int) millisUntilFinished;
                    iTestView.updateTimer((int) millisUntilFinished);
                }
            }

            public void onFinish() {
                iTestView.updateTimer(0);
                mTime = false;
                testResult();
            }

        }.start();
    }

}
