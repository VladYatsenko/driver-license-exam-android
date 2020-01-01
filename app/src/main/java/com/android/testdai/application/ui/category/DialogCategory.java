package com.android.testdai.application.ui.category;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.testdai.R;
import com.android.testdai.application.ui.category.abstraction.ICategoryView;
import com.android.testdai.application.ui.category.model.Category;
import com.android.testdai.application.ui.category.model.GroupEnum;
import com.android.testdai.application.ui.main.MainActivity;
import com.android.testdai.util.AnalyticUtil;

import java.util.List;


public class DialogCategory extends DialogFragment implements ICategoryView {

    private CategoryPresenter presenter;
    private RecyclerView categoryRecyclervView;
    private CategoryAdapter categoryAdapter;

    private AlertDialog dialog;

    private static final String ARG_CATEGORY = "category";
    public static final String EXTRA_CATEGORY =
            "com.example.android.testdai.category";


    public static DialogCategory newInstance(String category){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        DialogCategory fragment = new DialogCategory();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_category, null);
        AnalyticUtil.getInstance(getActivity().getApplicationContext()).logScreenEvent(getClass().getSimpleName());


        presenter = new CategoryPresenter(this);

        categoryRecyclervView = (RecyclerView) v.findViewById(R.id.category_recycler);
        categoryRecyclervView.setLayoutManager(new GridLayoutManager(getActivity(), 5));

        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.select_category)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                    presenter.getResult();
                            }
                        });

        dialog = builder.create();
        dialog.show();

        return  dialog;

    }

    @Override
    public void onResume() {
        super.onResume();

        String listCatetorys = (String) getArguments().getSerializable(ARG_CATEGORY);
        presenter.attachView(listCatetorys);

    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayout;
        private TextView categoryText;

        CategoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_category, parent,false));

            itemView.setOnClickListener((View.OnClickListener) this);
            categoryText = (TextView) itemView.findViewById(R.id.category_text);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.category_layout);

        }

        public void bind(Category category){

            if(!category.getGroup().getGroupName().equals(GroupEnum.EMPTY)){
                categoryText.setText(getActivity().getString(category.getName()));
                if(category.getGroup().isEnabled()) {
                    if(category.isSelected()){
                        linearLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_category));
                        categoryText.setTextColor(getResources().getColor(R.color.selected));
                    }else{
                        linearLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.not_selected));
                        categoryText.setTextColor(getResources().getColor(R.color.enable));
                    }
                }
                else{
                    linearLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.disable));
                    categoryText.setTextColor(getResources().getColor(R.color.disable));
                }

            }

        }

        @Override
        public void onClick (View view){
            presenter.onClick(this.getAdapterPosition());
        }

    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<Category> categories;

        public CategoryAdapter(List<Category> categories){
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CategoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category = categories.get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

    }

    @Override
    public void updateUI(List<Category> categories, boolean okState) {

        categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclervView.setAdapter(categoryAdapter);
        setButtonEnabled(okState);

    }

    private void setButtonEnabled(boolean state){

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(state);

    }

    @Override
    public void sendResult(String categorys) {

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY, categorys);
        ((MainActivity)getActivity()).onActivityResult(1,1,intent);

    }

}