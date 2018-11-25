package com.android.testdai.application.domain.category.abstraction;

import com.android.testdai.application.domain.category.model.Category;

import java.util.List;

import io.reactivex.Single;

public interface ICategoryInteractor {

    String getCategory();

    Single<List<Category>> getListCategory();

    void saveCategory(List<Category> categories);

}
