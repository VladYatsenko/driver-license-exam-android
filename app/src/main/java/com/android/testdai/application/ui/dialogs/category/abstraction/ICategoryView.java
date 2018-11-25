package com.android.testdai.application.ui.dialogs.category.abstraction;

import android.arch.lifecycle.LifecycleOwner;

import com.android.testdai.application.domain.category.model.Category;

import java.util.List;

public interface ICategoryView extends LifecycleOwner {

    void updateUI(List<Category> categories, boolean okState);

}
