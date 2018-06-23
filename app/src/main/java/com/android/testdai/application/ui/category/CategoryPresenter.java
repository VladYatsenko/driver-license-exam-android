package com.android.testdai.application.ui.category;

import android.content.Intent;

import com.android.testdai.R;
import com.android.testdai.application.ui.category.abstraction.ICategoryView;
import com.android.testdai.application.ui.category.model.Category;
import com.android.testdai.application.ui.category.model.Group;

import java.util.ArrayList;

import static com.android.testdai.application.ui.category.model.GroupEnum.ABCD;
import static com.android.testdai.application.ui.category.model.GroupEnum.C1E_BE_CE;
import static com.android.testdai.application.ui.category.model.GroupEnum.D1E_DE;
import static com.android.testdai.application.ui.category.model.GroupEnum.EMPTY;
import static com.android.testdai.application.ui.category.model.GroupEnum.T;

public class CategoryPresenter {

    private ArrayList<Category> categories;
    private ArrayList<Group> groups;
    private String preferencesCategory;
    private DialogCategory context;
    private ICategoryView categoryView;

    public static final String EXTRA_CATEGORY =
            "com.example.android.testdai.categories";

    CategoryPresenter(DialogCategory context){

        this.context = context;
        categoryView = (ICategoryView) context;

        groups = new ArrayList<>();
        groups.add(new Group(ABCD));
        groups.add(new Group(C1E_BE_CE));
        groups.add(new Group(D1E_DE));
        groups.add(new Group(T));
        groups.add(new Group(EMPTY));

        categories = new ArrayList<>();

        categories.add(new Category(R.string.A1, groups.get(0)));//0
        categories.add(new Category(R.string.B1, groups.get(0)));//1
        categories.add(new Category(R.string.C1, groups.get(0)));//2
        categories.add(new Category(R.string.D1, groups.get(0)));//3
        categories.add(new Category(R.string.empty, groups.get(4)));//4

        categories.add(new Category(R.string.empty,groups.get(4)));//5
        categories.add(new Category(R.string.empty, groups.get(4)));//6
        categories.add(new Category(R.string.C1E, groups.get(1)));//7
        categories.add(new Category(R.string.D1E, groups.get(2)));//8
        categories.add(new Category(R.string.empty, groups.get(4)));//9

        categories.add(new Category(R.string.A, groups.get(0)));//10
        categories.add(new Category(R.string.B, groups.get(0)));//11
        categories.add(new Category(R.string.C, groups.get(0)));//12
        categories.add(new Category(R.string.D, groups.get(0)));//13
        categories.add(new Category(R.string.T, groups.get(3)));//14

        categories.add(new Category(R.string.empty, groups.get(4)));//15
        categories.add(new Category(R.string.BE, groups.get(1)));//16
        categories.add(new Category(R.string.CE, groups.get(1)));//17
        categories.add(new Category(R.string.DE, groups.get(2)));//18
        categories.add(new Category(R.string.empty, groups.get(4)));//19

    }

    public void attachView(String categories){

        loadCategories(categories);
        updateUI(true);

    }

    private void loadCategories(String categories){

        String[] arrayCategory = categories.split(";");

        for(String category: arrayCategory){
            for(Category c: this.categories) {
                if (category.equals(context.getActivity().getString(c.getName()))){
                    c.setSelected(true);
                }
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
        categoryView.updateUI(this.categories, okState);
    }

    public void getResult(){

        String category = "";
//        for(Category c:categories){
//            if(c.isSelected()){
//                category += context.getActivity().getString(c.getName())+";";
//            }
//        }
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

        categoryView.sendResult(category);

    }

}
