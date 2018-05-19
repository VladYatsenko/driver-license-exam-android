package com.android.testdai.application.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.testdai.R;


public class DialogCategory extends DialogFragment {

    CheckBox mCategoryA1, mCategoryB1, mCategoryC1, mCategoryD1,
            mCategoryC1E, mCategoryD1E,
            mCategoryA, mCategoryB, mCategoryC, mCategoryD, mCategoryT,
            mCategoryBE, mCategoryCE, mCategoryDE;
    String mCategory = "";
    private final int SELECTED = R.drawable.not_selected_true;
    private final int NOT_SELECTED = R.drawable.not_selected;
    private final int DISABLE = R.drawable.disable;

    String enable = "#000000";
    String disable = "#CBCBCB";
    String selected = "#ffffff";

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

        String listCatetorys = (String) getArguments().getSerializable(ARG_CATEGORY);
        String[] categorys = listCatetorys.split(";");

        mCategoryA1 = (CheckBox) v.findViewById(R.id.categoryA1);
        mCategoryA1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(true,null,null,null,
                            false,false,
                            null,null,null,null,false,
                            false,false,false);

                    categoryBackground(SELECTED, null, null, null,
                            DISABLE, DISABLE,
                            null, null, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryA1.setBackgroundResource(NOT_SELECTED);
                    mCategoryA1.setTextColor(Color.parseColor(enable));

                    if(!mCategoryA.isChecked()&&!mCategoryB.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(NOT_SELECTED, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryB1 = (CheckBox) v.findViewById(R.id.categoryB1);
        mCategoryB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(null,true,null,null,
                            false,false,
                            null,null,null,null,false,
                            false,false,false);

                    categoryBackground(null, SELECTED, null, null,
                            DISABLE, DISABLE,
                            null, null, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryB1.setBackgroundResource(NOT_SELECTED);
                    mCategoryB1.setTextColor(Color.parseColor(enable));
                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                            categoryEnabled(true,true,true,true,
                                    true,true,
                                    true,true,true,true,true,
                                    true,true,true);
                            categoryBackground(null, null, null, null,
                                    NOT_SELECTED, NOT_SELECTED,
                                    null, null, null, null, NOT_SELECTED,
                                    NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryC1 = (CheckBox) v.findViewById(R.id.categoryC1);
        mCategoryC1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    mCategoryC1.setBackgroundResource(SELECTED);
                    //mCategoryB1.setTextColor(Color.WHITE);
                    categoryEnabled(null,null,true,null,
                            false,false,
                            null,null,null,null,false,
                            false,false,false);

                    categoryBackground(null, null, SELECTED, null,
                            DISABLE, DISABLE,
                            null, null, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryC1.setBackgroundResource(NOT_SELECTED);
                    mCategoryC1.setTextColor(Color.parseColor(enable));

                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryD1 = (CheckBox) v.findViewById(R.id.categoryD1);
        mCategoryD1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(null,null,null,true,
                            false,false,
                            null,null,null,null,false,
                            false,false,false);

                    categoryBackground(null, null, null, SELECTED,
                            DISABLE, DISABLE,
                            null, null, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryD1.setBackgroundResource(NOT_SELECTED);
                    mCategoryD1.setTextColor(Color.parseColor(enable));

                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryB.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryC1E = (CheckBox) v.findViewById(R.id.categoryC1E);
        mCategoryC1E.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){

                    categoryEnabled(false,false,false,false,
                            true,false,
                            false,false,false,false,false,
                            null,null,false);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            SELECTED, DISABLE,
                            DISABLE, DISABLE, DISABLE, DISABLE, DISABLE,
                            null, null, DISABLE);
                } else {
                    mCategoryC1E.setBackgroundResource(NOT_SELECTED);
                    mCategoryC1E.setTextColor(Color.parseColor(enable));
                    if(!mCategoryBE.isChecked()&&!mCategoryCE.isChecked()) {
                        categoryEnabled(true, true, true, true,
                                true, true,
                                true, true, true, true, true,
                                true, true, true);
                        categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryD1E = (CheckBox) v.findViewById(R.id.categoryD1E);
        mCategoryD1E.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(false,false,false,false,
                            false,true,
                            false,false,false,false,false,
                            false,false,null);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            DISABLE, SELECTED,
                            DISABLE, DISABLE, DISABLE, DISABLE, DISABLE,
                            DISABLE, DISABLE, null);
                } else {
                    mCategoryD1E.setBackgroundResource(NOT_SELECTED);
                    mCategoryD1E.setTextColor(Color.parseColor(enable));
                    if(!mCategoryDE.isChecked()){
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryA = (CheckBox) v.findViewById(R.id.categoryA);
        mCategoryA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){

                    categoryEnabled(null,null,null,null,
                            false,false,
                            true,null,null,null,false,
                            false,false,false);

                    categoryBackground(null, null, null, null,
                            DISABLE, DISABLE,
                            SELECTED, null, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryA.setBackgroundResource(NOT_SELECTED);
                    mCategoryA.setTextColor(Color.parseColor(enable));
                    if(!mCategoryA1.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryB.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryB = (CheckBox) v.findViewById(R.id.categoryB);
        mCategoryB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    mCategoryB.setBackgroundResource(SELECTED);
                    //mCategoryB1.setTextColor(Color.WHITE);
                    categoryEnabled(null,null,null,null,
                            false,false,
                            null,true,null,null,false,
                            false,false,false);

                    categoryBackground(null, null, null, null,
                            DISABLE, DISABLE,
                            null, SELECTED, null, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryB.setBackgroundResource(NOT_SELECTED);
                    mCategoryB.setTextColor(Color.parseColor(enable));
                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryC = (CheckBox) v.findViewById(R.id.categoryC);
        mCategoryC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(null,null,null,null,
                            false,false,
                            null,null,true,null,false,
                            false,false,false);

                    categoryBackground(null, null, null, null,
                            DISABLE, DISABLE,
                            null, null, SELECTED, null, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryC.setBackgroundResource(NOT_SELECTED);
                    mCategoryC.setTextColor(Color.parseColor(enable));
                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryB.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryD1.isChecked()&&!mCategoryD.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryD = (CheckBox) v.findViewById(R.id.categoryD);
        mCategoryD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(null,null,null,null,
                            false,false,
                            null,null,null,true,false,
                            false,false,false);

                    categoryBackground(null, null, null, null,
                            DISABLE, DISABLE,
                            null, null, null, SELECTED, DISABLE,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    mCategoryD.setBackgroundResource(NOT_SELECTED);
                    mCategoryD.setTextColor(Color.parseColor(enable));
                    if(!mCategoryA1.isChecked()&&!mCategoryA.isChecked()&&!mCategoryB1.isChecked()&&!mCategoryB.isChecked()&&!mCategoryC1.isChecked()&&!mCategoryC.isChecked()&&!mCategoryD1.isChecked()) {
                        categoryEnabled(true,true,true,true,
                                true,true,
                                true,true,true,true,true,
                                true,true,true);
                        categoryBackground(null, null, null, null,
                                NOT_SELECTED, NOT_SELECTED,
                                null, null, null, null, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryT = (CheckBox) v.findViewById(R.id.categoryT);
        mCategoryT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    mCategoryT.setBackgroundResource(SELECTED);
                    //mCategoryT.setTextColor(Color.WHITE);
                    categoryEnabled(false,false,false,false,
                            false,false,
                            false,false,false,false,true,
                            false,false,false);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            DISABLE, DISABLE,
                            DISABLE, DISABLE, DISABLE, DISABLE, SELECTED,
                            DISABLE, DISABLE, DISABLE);
                } else {
                    categoryEnabled(true,true,true,true,
                            true,true,
                            true,true,true,true,true,
                            true,true,true);
                    categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED,
                            NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                }
            }
        });
        mCategoryBE = (CheckBox) v.findViewById(R.id.categoryBE);
        mCategoryBE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(false,false,false,false,
                            null,false,
                            false,false,false,false,false,
                            true,null,false);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            null, DISABLE,
                            DISABLE, DISABLE, DISABLE, DISABLE, DISABLE,
                            SELECTED, null, DISABLE);
                } else {
                    mCategoryBE.setBackgroundResource(NOT_SELECTED);
                    mCategoryBE.setTextColor(Color.parseColor(enable));
                    if(!mCategoryC1E.isChecked()&&!mCategoryCE.isChecked()) {
                        categoryEnabled(true, true, true, true,
                                true, true,
                                true, true, true, true, true,
                                true, true, true);
                        categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryCE = (CheckBox) v.findViewById(R.id.categoryCE);
        mCategoryCE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(false,false,false,false,
                            null,false,
                            false,false,false,false,false,
                            null,true,false);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            null, DISABLE,
                            DISABLE, DISABLE, DISABLE, DISABLE, DISABLE,
                            null, SELECTED, DISABLE);
                } else {
                    mCategoryCE.setBackgroundResource(NOT_SELECTED);
                    mCategoryCE.setTextColor(Color.parseColor(enable));
                    if(!mCategoryC1E.isChecked()&&!mCategoryBE.isChecked()) {
                        categoryEnabled(true, true, true, true,
                                true, true,
                                true, true, true, true, true,
                                true, true, true);
                        categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                                NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });
        mCategoryDE = (CheckBox) v.findViewById(R.id.categoryDE);
        mCategoryDE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categoryString();
                if(isChecked){
                    categoryEnabled(false,false,false,false,
                            false,null,
                            false,false,false,false,false,
                            false,false,true);

                    categoryBackground(DISABLE, DISABLE, DISABLE, DISABLE,
                            DISABLE, null,
                            DISABLE, DISABLE, DISABLE, DISABLE, DISABLE,
                            DISABLE, DISABLE, SELECTED);
                } else {
                    mCategoryDE.setBackgroundResource(NOT_SELECTED);
                    mCategoryDE.setTextColor(Color.parseColor(enable));
                    if(!mCategoryD1E.isChecked()){
                        categoryEnabled(true,true,true,true,
                            true,true,
                            true,true,true,true,true,
                            true,true,true);
                        categoryBackground(NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                            NOT_SELECTED, NOT_SELECTED,
                            NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED, NOT_SELECTED,
                            NOT_SELECTED, NOT_SELECTED, NOT_SELECTED);
                    }
                }
            }
        });



        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.category)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //if(mCategory.length()!=0){
                                    sendResult(1, categoryString());
                                //}
                            }
                        });
                //.create();
        dialog = builder.create();
        dialog.show();
        if(mCategory.length()==0) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }

        for(String t: categorys){
            if(t.equals("A1")){
                mCategoryA1.setChecked(true);
            }
            if(t.equals("B1")){
                mCategoryB1.setChecked(true); ;
            }
            if(t.equals("C1")){
                mCategoryC1.setChecked(true);
            }
            if(t.equals("D1")){
                mCategoryD1.setChecked(true);
            }
            if(t.equals("C1E")){
                mCategoryC1E.setChecked(true);
            }
            if(t.equals("D1E")){
                mCategoryD1E.setChecked(true);
            }
            if(t.equals("A")){
                mCategoryA.setChecked(true);
            }
            if(t.equals("B")){
                mCategoryB.setChecked(true);
            }
            if(t.equals("C")){
                mCategoryC.setChecked(true);
            }
            if(t.equals("D")){
                mCategoryD.setChecked(true);
            }
            if(t.equals("T")){
                mCategoryT.setChecked(true);
            }
            if(t.equals("BE")){
                mCategoryBE.setChecked(true);
            }
            if(t.equals("CE")){
                mCategoryCE.setChecked(true);
            }
            if(t.equals("DE")){
                mCategoryDE.setChecked(true);
            }
        }

        return  dialog;
    }

    @SuppressLint("ResourceAsColor")
    private void categoryEnabled(Boolean A1, Boolean B1, Boolean C1, Boolean D1,
                                 Boolean C1E, Boolean D1E,
                                 Boolean A, Boolean B, Boolean C, Boolean D, Boolean T,
                                 Boolean BE, Boolean CE, Boolean DE){
        if(A1!=null){
            mCategoryA1.setEnabled(A1);
            if(A1){
                mCategoryA1.setTextColor(Color.parseColor(enable));
            }else if(!A1){
                mCategoryA1.setTextColor(Color.parseColor(disable));
            }
        }
        if(B1!=null){
            mCategoryB1.setEnabled(B1);
            if(B1){
                mCategoryB1.setTextColor(Color.parseColor(enable));
            }else if(!B1){
                mCategoryB1.setTextColor(Color.parseColor(disable));
            }
        }
        if(C1!=null){
            mCategoryC1.setEnabled(C1);
            if(C1){
                mCategoryC1.setTextColor(Color.parseColor(enable));
            }else if(!C1){
                mCategoryC1.setTextColor(Color.parseColor(disable));
            }
        }
        if(D1!=null){
            mCategoryD1.setEnabled(D1);
            if(D1){
                mCategoryD1.setTextColor(Color.parseColor(enable));
            }else if(!D1){
                mCategoryD1.setTextColor(Color.parseColor(disable));
            }
        }
        if(C1E!=null){
            mCategoryC1E.setEnabled(C1E);
            if(C1E){
                mCategoryC1E.setTextColor(Color.parseColor(enable));
            }else if(!C1E){
                mCategoryC1E.setTextColor(Color.parseColor(disable));
            }
        }
        if(D1E!=null){
            mCategoryD1E.setEnabled(D1E);
            if(D1E){
                mCategoryD1E.setTextColor(Color.parseColor(enable));
            }else if(!D1E){
                mCategoryD1E.setTextColor(Color.parseColor(disable));
            }
        }
        if(A!=null){
            mCategoryA.setEnabled(A);
            if(A){
                mCategoryA.setTextColor(Color.parseColor(enable));
            }else if(!A){
                mCategoryA.setTextColor(Color.parseColor(disable));
            }
        }
        if(B!=null){
            mCategoryB.setEnabled(B);
            if(B){
                mCategoryB.setTextColor(Color.parseColor(enable));
            }else if(!B){
                mCategoryB.setTextColor(Color.parseColor(disable));
            }
        }
        if(C!=null){
            mCategoryC.setEnabled(C);
            if(C){
                mCategoryC.setTextColor(Color.parseColor(enable));
            }else if(!C){
                mCategoryC.setTextColor(Color.parseColor(disable));
            }
        }
        if(D!=null){
            mCategoryD.setEnabled(D);
            if(D){
                mCategoryD.setTextColor(Color.parseColor(enable));
            }else if(!D){
                mCategoryD.setTextColor(Color.parseColor(disable));
            }
        }
        if(T!=null){
            mCategoryT.setEnabled(T);
            if(T){
                mCategoryT.setTextColor(Color.parseColor(enable));
            }else if(!T){
                mCategoryT.setTextColor(Color.parseColor(disable));
            }
        }
        if(BE!=null){
            mCategoryBE.setEnabled(BE);
            if(BE){
                mCategoryBE.setTextColor(Color.parseColor(enable));
            }else if(!BE){
                mCategoryBE.setTextColor(Color.parseColor(disable));
            }
        }
        if(CE!=null){
            mCategoryCE.setEnabled(CE);
            if(CE){
                mCategoryCE.setTextColor(Color.parseColor(enable));
            }else if(!CE){
                mCategoryCE.setTextColor(Color.parseColor(disable));
            }
        }
        if(DE!=null){
            mCategoryDE.setEnabled(DE);
            if(DE){
                mCategoryDE.setTextColor(Color.parseColor(enable));
            }else if(!DE){
                mCategoryDE.setTextColor(Color.parseColor(disable));
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void categoryBackground(Integer A1, Integer B1, Integer C1, Integer D1,
                                    Integer C1E, Integer D1E,
                                    Integer A, Integer B, Integer C, Integer D, Integer T,
                                    Integer BE, Integer CE, Integer DE){
        if(A1!=null){
            mCategoryA1.setBackgroundResource(A1);
            if(A1==SELECTED){
                mCategoryA1.setTextColor(Color.parseColor(selected));
            }
        }
        if(B1!=null){
            mCategoryB1.setBackgroundResource(B1);
            if(B1==SELECTED){
                mCategoryB1.setTextColor(Color.parseColor(selected));
            }
        }
        if(C1!=null){
            mCategoryC1.setBackgroundResource(C1);
            if(C1==SELECTED){
                mCategoryC1.setTextColor(Color.parseColor(selected));
            }
        }
        if(D1!=null){
            mCategoryD1.setBackgroundResource(D1);
            if(D1==SELECTED){
                mCategoryD1.setTextColor(Color.parseColor(selected));
            }
        }
        if(C1E!=null){
            mCategoryC1E.setBackgroundResource(C1E);
            if(C1E==SELECTED){
                mCategoryC1E.setTextColor(Color.parseColor(selected));
            }
        }
        if(D1E!=null){
            mCategoryD1E.setBackgroundResource(D1E);
            if(D1E==SELECTED){
                mCategoryD1E.setTextColor(Color.parseColor(selected));
            }
        }
        if(A!=null){
            mCategoryA.setBackgroundResource(A);
            if(A==SELECTED){
                mCategoryA.setTextColor(Color.parseColor(selected));
            }
        }
        if(B!=null){
            mCategoryB.setBackgroundResource(B);
            if(B==SELECTED){
                mCategoryB.setTextColor(Color.parseColor(selected));
            }
        }
        if(C!=null){
            mCategoryC.setBackgroundResource(C);
            if(C==SELECTED){
                mCategoryC.setTextColor(Color.parseColor(selected));
            }
        }
        if(D!=null){
            mCategoryD.setBackgroundResource(D);
            if(D==SELECTED){
                mCategoryD.setTextColor(Color.parseColor(selected));
            }
        }
        if(T!=null){
            mCategoryT.setBackgroundResource(T);
            if(T==SELECTED){
                mCategoryT.setTextColor(Color.parseColor(selected));
            }
        }
        if(BE!=null){
            mCategoryBE.setBackgroundResource(BE);
            if(BE==SELECTED){
                mCategoryBE.setTextColor(Color.parseColor(selected));
            }
        }
        if(CE!=null){
            mCategoryCE.setBackgroundResource(CE);
            if(CE==SELECTED){
                mCategoryCE.setTextColor(Color.parseColor(selected));
            }
        }
        if(DE!=null){
            mCategoryDE.setBackgroundResource(DE);
            if(DE==SELECTED){
                mCategoryDE.setTextColor(Color.parseColor(selected));
            }
        }
    }

    private String categoryString(){
        mCategory = "";

        if(mCategoryA.isChecked()){
            mCategory += "A;";
        }
        if(mCategoryA1.isChecked()){
            mCategory += "A1;";
        }
        if(mCategoryB.isChecked()){
            mCategory += "B;";
        }
        if(mCategoryB1.isChecked()){
            mCategory += "B1;";
        }
        if(mCategoryC.isChecked()){
            mCategory += "C;";
        }
        if(mCategoryC1.isChecked()){
            mCategory += "C1;";
        }
        if(mCategoryD.isChecked()){
            mCategory += "D;";
        }
        if(mCategoryD1.isChecked()){
            mCategory += "D1;";
        }
        if(mCategoryC1E.isChecked()){
            mCategory += "C1E;";
        }
        if(mCategoryD1E.isChecked()){
            mCategory += "D1E;";
        }
        if(mCategoryBE.isChecked()){
            mCategory += "BE;";
        }
        if(mCategoryCE.isChecked()){
            mCategory += "CE;";
        }
        if(mCategoryDE.isChecked()){
            mCategory += "DE;";
        }
        if(mCategoryT.isChecked()){
            mCategory += "T;";
        }

        if(mCategory.length()==0) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }else{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }

        return mCategory;
    }

    private void sendResult(int resultCode, String category){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY, category);
        ((MainActivity)getActivity()).onActivityResult(1,resultCode,intent);
    }
}