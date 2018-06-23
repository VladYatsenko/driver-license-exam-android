package com.android.testdai.application.ui.category.abstraction;

import com.android.testdai.application.ui.category.model.Category;

import java.util.List;

public interface ICategoryView {

    void updateUI(List<Category> categories, boolean okState);

    void sendResult(String categorys);

}
