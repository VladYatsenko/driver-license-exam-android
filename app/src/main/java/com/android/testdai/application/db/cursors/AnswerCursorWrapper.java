package com.android.testdai.application.db.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.testdai.application.db.DbSchema;
import com.android.testdai.model.QuestionEntity;

public class AnswerCursorWrapper  extends CursorWrapper{

    public AnswerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

//    public Answer getAnswer(){
//        String textAnswer = getString(getColumnIndex(DbSchema.AnswerTable.Cols.TEXTANSWER));
//        int correct = getInt(getColumnIndex(DbSchema.AnswerTable.Cols.ANSWERTRUE));
//
//        QuestionEntity.Answer answer = new QuestionEntity.Answer();
//        answer.setTextAnswer(textAnswer);
//        answer.setAnswerTrue(correct!=0);
//        answer.setSelected(false);
//        answer.setChosen(false);
//        return answer;
//
//    }
}