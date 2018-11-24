package com.android.testdai.application.db.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.testdai.application.db.DbSchema;
import com.android.testdai.application.ui.activities.test.model.Answer;
import com.android.testdai.application.ui.activities.test.model.Question;

import java.util.List;

public class QuestionCursorWrapper extends CursorWrapper {

    public QuestionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Question getQuestion(List<Answer> answers){
        int idString = getInt(getColumnIndex((DbSchema.QuestionTable.Cols.ID)));
        String textQuestion = getString(getColumnIndex(DbSchema.QuestionTable.Cols.TEXTQUESTION));
        String imageSource = getString(getColumnIndex(DbSchema.QuestionTable.Cols.IMAGESOURCE));

        Question question = new Question();
        question.setId(idString);
        question.setTextQuestion(textQuestion);
        question.setAnswered(false);
        question.setSelected(false);
        question.setImageSource(imageSource);
        if (answers!=null)
            question.setAnswers(answers);

        return question;
    }

}