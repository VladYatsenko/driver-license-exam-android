package com.android.testdai.ui.test.abstraction;


import androidx.lifecycle.LifecycleOwner;

import com.android.testdai.model.QuestionEntity;

import java.util.List;

public interface ITestView extends LifecycleOwner {

    void startLoading();

    void stopLoading();

    void updateListQuestion(int position, List<QuestionEntity> questionEntities);

    void updateQuestion(QuestionEntity questionEntity);

    void updateTimer(int time);

    void showResultDialog(int result);

}
