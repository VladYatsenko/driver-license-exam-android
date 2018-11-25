package com.android.testdai.application.data.local.db.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.testdai.application.data.local.db.DbSchema;
import com.android.testdai.application.ui.activities.test.model.Answer;

public class AnswerCursorWrapper  extends CursorWrapper{

    public AnswerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Answer getAnswer(){
        String textAnswer = getString(getColumnIndex(DbSchema.AnswerTable.Cols.TEXTANSWER));
        int correct = getInt(getColumnIndex(DbSchema.AnswerTable.Cols.ANSWERTRUE));

        Answer answer = new Answer();
        answer.setTextAnswer(textAnswer);
        answer.setAnswerTrue(correct!=0);
        answer.setSelected(false);
        answer.setChosen(false);
        return answer;

    }
}