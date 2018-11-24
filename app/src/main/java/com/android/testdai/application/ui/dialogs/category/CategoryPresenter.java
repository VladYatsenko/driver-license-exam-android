package com.android.testdai.application.ui.dialogs.category;

import com.android.testdai.R;
import com.android.testdai.application.ui.abstractions.AbstractPresenter;
import com.android.testdai.application.ui.dialogs.category.abstraction.ICategoryView;
import com.android.testdai.application.domain.question.model.Category;
import com.android.testdai.application.domain.question.model.Group;
import com.android.testdai.di.DIProvider;

import java.util.ArrayList;

import static com.android.testdai.application.domain.question.model.GroupEnum.ABCD;
import static com.android.testdai.application.domain.question.model.GroupEnum.C1E_BE_CE;
import static com.android.testdai.application.domain.question.model.GroupEnum.D1E_DE;
import static com.android.testdai.application.domain.question.model.GroupEnum.EMPTY;
import static com.android.testdai.application.domain.question.model.GroupEnum.T;

public class CategoryPresenter extends AbstractPresenter<ICategoryView> {



    CategoryPresenter(){

        DIProvider.getDomainComponent().inject(this);


    }


    public void attachView(String categories){

        loadCategories(categories);
        updateUI(true);

    }

    private void loadCategories(String categories){

        String[] arrayCategory = categories.split(";");

        for(String category: arrayCategory){
            for(Category c: this.categories) {
                //if (category.equals(view.getActivity().getString(c.getName()))){
                    c.setSelected(true);
                //}
            }
        }

        for(Category category:this.categories){
            if(category.isSelected()){
                for(Category c:this.categories){
                    if(c.getGroup().equals(category.getGroup()))
                        c.getGroup().setEnabled(true);
                    else
                        c.getGroup().setEnabled(false);
                }
            }
        }

    }

    public void onClick(int position){

        Category category = categories.get(position);

        if(!category.getGroup().getGroupName().equals(EMPTY)) {
            if (category.getGroup().isEnabled()) {
                categories.get(position).setSelected(!category.isSelected());

                if (isLastItemOfGroup(category)) {
                    for (Group g : groups) {
                        g.setEnabled(true);
                        updateUI(false);
                    }
                } else {
                    for (Group g : groups) {
                        if (!g.equals(category.getGroup()))
                            g.setEnabled(false);
                            updateUI(true);
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

    private void updateUI(boolean okState){
        view.updateUI(this.categories, okState);
    }

    void getResult(){

        String category = "";
//        for(Category c:categories){
//            if(c.isSelected()){
//                category += view.getActivity().getString(c.getName())+";";
//            }
//        }

        //сделал так, чтоб соблюдалась последовательность (A1;A;B1;B; и тд)
        if(categories.get(0).isSelected()){
            category += "A1;";
        }
        if(categories.get(10).isSelected()){
            category += "A;";
        }
        if(categories.get(1).isSelected()){
            category += "B1;";
        }
        if(categories.get(11).isSelected()){
            category += "B;";
        }
        if(categories.get(2).isSelected()){
            category += "C1;";
        }
        if(categories.get(12).isSelected()){
            category += "C;";
        }
        if(categories.get(3).isSelected()){
            category += "D1;";
        }
        if(categories.get(13).isSelected()){
            category += "D;";
        }
        if(categories.get(7).isSelected()){
            category += "C1E;";
        }
        if(categories.get(8).isSelected()){
            category += "D1E;";
        }
        if(categories.get(16).isSelected()){
            category += "BE;";
        }
        if(categories.get(17).isSelected()){
            category += "CE;";
        }
        if(categories.get(18).isSelected()){
            category += "DE;";
        }
        if(categories.get(14).isSelected()){
            category += "T;";
        }

        //AnalyticUtil.getInstance(view.getActivity().getApplicationContext()).logButtonEvent(category);
        //PreferencesUtil.getInstance(view.getActivity().getApplicationContext()).setCategory(category);
        view.sendResult(category);

    }

}
