package com.android.testdai.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.android.testdai.Model.Question;
import com.android.testdai.Model.Question.Answer;

import java.util.List;

public class QuestionCursorWrapper extends CursorWrapper {

    public QuestionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Question getQuestion(List<Answer> answers){
        int idString = getInt(getColumnIndex((QuestionDbSchema.QuestionTable.Cols.ID)));
        String textQuestion = getString(getColumnIndex(QuestionDbSchema.QuestionTable.Cols.TEXTQUESTION));
        String imageSource = getString(getColumnIndex(QuestionDbSchema.QuestionTable.Cols.IMAGESOURCE));

        Question question = new Question();
        question.setId(idString);
        question.setTextQuestion(textQuestion);
        question.setWasAnswered(false);
        question.setWasSelected(false);
        question.setImageSource(imageSource);
        question.setAnswer(answers);

        return question;
    }

}