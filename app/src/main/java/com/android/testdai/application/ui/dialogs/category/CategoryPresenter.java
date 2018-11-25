package com.android.testdai.application.ui.dialogs.category;

import com.android.testdai.application.domain.category.abstraction.ICategoryInteractor;
import com.android.testdai.application.domain.category.model.Category;
import com.android.testdai.application.domain.category.model.Group;
import com.android.testdai.application.ui.activities.main.MainActivity;
import com.android.testdai.application.ui.dialogs.abstraction.AbstractDialogPresenter;
import com.android.testdai.application.ui.dialogs.category.abstraction.ICategoryView;
import com.android.testdai.di.DIProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.android.testdai.application.domain.category.CategoryInteractor.groups;
import static com.android.testdai.application.domain.category.model.GroupEnum.EMPTY;

public class CategoryPresenter extends AbstractDialogPresenter<ICategoryView> {

    @Inject
    protected ICategoryInteractor categoryInteractor;

    private List<Category> categories = new ArrayList<>();

    public CategoryPresenter(){
        DIProvider.getDomainComponent().inject(this);
    }

    public void attachView(ICategoryView view){
        super.attachView(view);
        showCategories();
    }

    private void showCategories(){
        disposables.add(categoryInteractor.getListCategory()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    this.categories = list;
                    view.updateUI(list, getOkState(list));
                }, Throwable::printStackTrace)

        );
    }

    private boolean getOkState(List<Category> list){
        for (Category category : list){
            if (category.getGroup().isEnabled())
                return true;
        }
        return false;
    }

    public void onClick(int position){

        Category category = categories.get(position);

        if(!category.getGroup().getGroupName().equals(EMPTY)) {
            if (category.getGroup().isEnabled()) {
                categories.get(position).setSelected(!category.isSelected());

                if (isLastItemOfGroup(category)) {
                    for (Group g : groups) {
                        g.setEnabled(true);
                        updateUI(categories, false);
                    }
                } else {
                    for (Group g : groups) {
                        if (!g.equals(category.getGroup()))
                            g.setEnabled(false);
                            updateUI(categories, true);
                    }
                }

            }

        }

    }

    private boolean isLastItemOfGroup(Category category){

        for(Category c:categories){
            if(c.isSelected() && c.getGroup().equals(category.getGroup()))
                return false;
        }

        return true;

    }

    private void updateUI(List<Category> list, boolean okState){
        view.updateUI(list, okState);
    }

    void onOkClick(){
        categoryInteractor.saveCategory(categories);
    }

    @Override
    protected void detachView() {
        super.detachView();
//        ((MainActivity)getActivity()).onActivityResult(1,1,intent);
    }

    @Override
    public void onDismissCommand() {

    }
}
