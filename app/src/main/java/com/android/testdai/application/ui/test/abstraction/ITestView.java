package com.android.testdai.application.ui.test.abstraction;

import android.arch.lifecycle.LifecycleOwner;

import com.android.testdai.application.model.Question;

import java.util.List;

public interface ITestView extends LifecycleOwner {

    void startLoading();

    void stopLoading();

    void updateListQuestion(int position, List<Question> questions);

    void updateQuestion(Question question);

    void updateTimer(int time);

    void showResultDialog(int result);

}
