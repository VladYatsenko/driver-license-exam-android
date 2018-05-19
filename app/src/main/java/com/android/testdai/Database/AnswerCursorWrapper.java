package com.android.testdai.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.testdai.Model.Question;

public class AnswerCursorWrapper  extends CursorWrapper{
    public AnswerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Question.Answer getAnswer(){
        String textAnswer = getString(getColumnIndex(QuestionDbSchema.AnswerTable.Cols.TEXTANSWER));
        int correct = getInt(getColumnIndex(QuestionDbSchema.AnswerTable.Cols.ANSWERTRUE));

        Question.Answer answer = new Question.Answer();
        answer.setTextAnswer(textAnswer);
        answer.setAnswerTrue(correct!=0);
        answer.setWasChoosen(false);
        return answer;

    }
}