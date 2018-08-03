package com.android.testdai.application.db.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.testdai.application.db.DbSchema;
import com.android.testdai.application.model.Question;

public class AnswerCursorWrapper  extends CursorWrapper{

    public AnswerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Question.Answer getAnswer(){
        String textAnswer = getString(getColumnIndex(DbSchema.AnswerTable.Cols.TEXTANSWER));
        int correct = getInt(getColumnIndex(DbSchema.AnswerTable.Cols.ANSWERTRUE));

        Question.Answer answer = new Question.Answer();
        answer.setTextAnswer(textAnswer);
        answer.setAnswerTrue(correct!=0);
        answer.setSelected(false);
        answer.setChosen(false);
        return answer;

    }
}