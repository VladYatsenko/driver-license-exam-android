package com.android.testdai.application.domain.category;

import com.android.testdai.application.data.local.prefs.abstraction.interfaces.IPrefsRepo;
import com.android.testdai.application.domain.category.abstraction.ICategoryInteractor;
import com.android.testdai.application.domain.category.model.Category;
import com.android.testdai.application.domain.category.model.Group;
import com.android.testdai.di.DIProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.android.testdai.application.domain.category.model.GroupEnum.ABCD;
import static com.android.testdai.application.domain.category.model.GroupEnum.C1E_BE_CE;
import static com.android.testdai.application.domain.category.model.GroupEnum.D1E_DE;
import static com.android.testdai.application.domain.category.model.GroupEnum.EMPTY;
import static com.android.testdai.application.domain.category.model.GroupEnum.T;

public class CategoryInteractor implements ICategoryInteractor {

    @Inject
    protected IPrefsRepo prefsRepo;

    private List<Category> categories;
    public static final List<Group> groups = new ArrayList<>();

    public CategoryInteractor() {

        DIProvider.getDataComponent().inject(this);

        groups.add(new Group(ABCD));
        groups.add(new Group(C1E_BE_CE));
        groups.add(new Group(D1E_DE));
        groups.add(new Group(T));
        groups.add(new Group(EMPTY));

        categories = new ArrayList<>();

        categories.add(new Category("A1", groups.get(0)));
        categories.add(new Category("B1", groups.get(0)));
        categories.add(new Category("C1", groups.get(0)));
        categories.add(new Category("D1", groups.get(0)));
        categories.add(new Category("", groups.get(4)));

        categories.add(new Category("", groups.get(4)));
        categories.add(new Category("", groups.get(4)));
        categories.add(new Category("C1E", groups.get(1)));
        categories.add(new Category("D1E", groups.get(2)));
        categories.add(new Category("", groups.get(4)));

        categories.add(new Category("A", groups.get(0)));
        categories.add(new Category("B", groups.get(0)));
        categories.add(new Category("C", groups.get(0)));
        categories.add(new Category("D", groups.get(0)));
        categories.add(new Category("T", groups.get(3)));

        categories.add(new Category("", groups.get(4)));
        categories.add(new Category("BE", groups.get(1)));
        categories.add(new Category("CE", groups.get(1)));
        categories.add(new Category("DE", groups.get(2)));
        categories.add(new Category("", groups.get(4)));

        loadFromPrefs();

    }

    private void loadFromPrefs(){

        String[] arrayCategory = prefsRepo.getCategory().split(";");

        for(String category: arrayCategory){
            for(Category c: this.categories) {
                if (category.equals(c.getName())){
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

    @Override
    public String getCategory() {
        return getStringCategory();
    }

    @Override
    public Single<List<Category>> getListCategory() {
        loadFromPrefs();
        return Single.just(categories);
    }


    @Override
    public void saveCategory(List<Category> categories) {

        this.categories = categories;
        prefsRepo.updateCategory(getStringCategory());

    }

    private String getStringCategory(){
        String category = "";

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

        return category;
    }

}
