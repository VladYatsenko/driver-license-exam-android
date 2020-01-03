package com.android.testdai.application.db.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;


import java.util.List;

public class QuestionCursorWrapper extends CursorWrapper {

    public QuestionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

//    public QuestionEntity getQuestion(List<Answer> answers){
//        int idString = getInt(getColumnIndex((DbSchema.QuestionTable.Cols.ID)));
//        String textQuestion = getString(getColumnIndex(DbSchema.QuestionTable.Cols.TEXTQUESTION));
//        String imageSource = getString(getColumnIndex(DbSchema.QuestionTable.Cols.IMAGESOURCE));
//
//        QuestionEntity questionEntity = new QuestionEntity();
//        questionEntity.setId(idString);
//        questionEntity.setTextQuestion(textQuestion);
//        questionEntity.setAnswered(false);
//        questionEntity.setSelected(false);
//        questionEntity.setImageSource(imageSource);
//        if (answers!=null)
//            questionEntity.setAnswers(answers);
//
//        return questionEntity;
//    }

}