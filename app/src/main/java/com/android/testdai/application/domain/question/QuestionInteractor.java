package com.android.testdai.application.domain.question;

import com.android.testdai.R;
import com.android.testdai.application.domain.question.abstraction.IQuestionInteractor;
import com.android.testdai.application.domain.question.model.Category;
import com.android.testdai.application.domain.question.model.Group;
import com.android.testdai.di.DIProvider;

import java.util.ArrayList;
import java.util.List;

import static com.android.testdai.application.domain.question.model.GroupEnum.ABCD;
import static com.android.testdai.application.domain.question.model.GroupEnum.C1E_BE_CE;
import static com.android.testdai.application.domain.question.model.GroupEnum.D1E_DE;
import static com.android.testdai.application.domain.question.model.GroupEnum.EMPTY;
import static com.android.testdai.application.domain.question.model.GroupEnum.T;

public class QuestionInteractor implements IQuestionInteractor {

    private List<Category> categories;
    private List<Group> groups;

    QuestionInteractor(){

        DIProvider.getDataComponent().inject(this);

        groups = new ArrayList<>();
        groups.add(new Group(ABCD));
        groups.add(new Group(C1E_BE_CE));
        groups.add(new Group(D1E_DE));
        groups.add(new Group(T));
        groups.add(new Group(EMPTY));

        categories = new ArrayList<>();

        categories.add(new Category(R.string.A1, groups.get(0)));
        categories.add(new Category(R.string.B1, groups.get(0)));
        categories.add(new Category(R.string.C1, groups.get(0)));
        categories.add(new Category(R.string.D1, groups.get(0)));
        categories.add(new Category(R.string.empty, groups.get(4)));

        categories.add(new Category(R.string.empty,groups.get(4)));
        categories.add(new Category(R.string.empty, groups.get(4)));
        categories.add(new Category(R.string.C1E, groups.get(1)));
        categories.add(new Category(R.string.D1E, groups.get(2)));
        categories.add(new Category(R.string.empty, groups.get(4)));

        categories.add(new Category(R.string.A, groups.get(0)));
        categories.add(new Category(R.string.B, groups.get(0)));
        categories.add(new Category(R.string.C, groups.get(0)));
        categories.add(new Category(R.string.D, groups.get(0)));
        categories.add(new Category(R.string.T, groups.get(3)));

        categories.add(new Category(R.string.empty, groups.get(4)));
        categories.add(new Category(R.string.BE, groups.get(1)));
        categories.add(new Category(R.string.CE, groups.get(1)));
        categories.add(new Category(R.string.DE, groups.get(2)));
        categories.add(new Category(R.string.empty, groups.get(4)));

    }


}
