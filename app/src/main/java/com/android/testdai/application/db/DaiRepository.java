package com.android.testdai.application.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.android.testdai.application.db.cursors.AnswerCursorWrapper;
import com.android.testdai.application.db.cursors.QuestionCursorWrapper;
import com.android.testdai.application.model.Question;
import com.android.testdai.util.DatabaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DaiRepository {

    private static DaiRepository daiRepository;
    private DatabaseUtil database;

    private DaiRepository(Context context) {

        database = new DatabaseUtil(context);

        try {
            database.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            database.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    public static DaiRepository get(Context context) {
        if (daiRepository == null) {
            daiRepository = new DaiRepository(context);
        }
        return daiRepository;
    }

    //request to DB
    private QuestionCursorWrapper queryQuestion(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(
                DbSchema.QuestionTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new QuestionCursorWrapper(cursor);

    }

    //request to DB
    private AnswerCursorWrapper queryAnswer(String[] whereArgs) {
        Cursor cursor = database.query(
                DbSchema.AnswerTable.NAME,
                null,
                DbSchema.AnswerTable.Cols.FKQUESTION + "=?",
                whereArgs,
                null,
                null,
                null
        );
        return new AnswerCursorWrapper(cursor);
    }

    //cursor to List
    public List<Question> getQuestionsList(String category) {

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < 20; i++)
            questions.add(randomQuestion(i, category));

        return questions;

    }

    //cursor to List
    private List<Question.Answer> getAnswers(String[] whereArgs) {
        List<Question.Answer> answers = new ArrayList<>();

        AnswerCursorWrapper cursor = queryAnswer(whereArgs);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                answers.add(cursor.getAnswer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return answers;
    }

    private Question randomQuestion(int questionNumber, String category) {

        List<Integer> topicsQuestion = listTopics(questionNumber, category);
        int randTopicId = topicsQuestion.get(random(topicsQuestion.size()));

        List<Question> questions = getQuestionsByTopic(randTopicId);

        int randQuestionId = questions.get(random(questions.size())).getId();

        return getQuestionById(randQuestionId);

    }

    private List<Question> getQuestionsByTopic(int topicId) {

        List<Question> questions = new ArrayList<>();

        QuestionCursorWrapper cursor = queryQuestion(DbSchema.QuestionTable.Cols.TOPIC + "=?", new String[]{String.valueOf(topicId)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                questions.add(cursor.getQuestion(null));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }


        return questions;
    }

    private Question getQuestionById(int id) {

        Question question = null;

        QuestionCursorWrapper cursor = queryQuestion(DbSchema.QuestionTable.Cols.ID + "=?",
                new String[]{String.valueOf(id)});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                question = (cursor.getQuestion(getAnswers(new String[]{String.valueOf(id)})));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return question;
    }

    private int random(int size) {

        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(size);

    }

    private List<Integer> listTopics(int questionNumber, String category) {
        List<Integer> topicsQuestion = new ArrayList<>();
        String[] categorys = category.split(";");
        switch (questionNumber) {
            case 0:
                topicsQuestion.add(2);
                topicsQuestion.add(4);
                topicsQuestion.add(5);
                topicsQuestion.add(6);
                topicsQuestion.add(7);
                topicsQuestion.add(20);
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(42);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(46);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(50);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(54);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(58);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(62);
                    }
                }
                return topicsQuestion;

            case 1:
                topicsQuestion.add(3);
                topicsQuestion.add(27);
                topicsQuestion.add(32);
                return topicsQuestion;
            case 2:
                topicsQuestion.add(35);
                return topicsQuestion;
            case 3:
                topicsQuestion.add(36);
                return topicsQuestion;
            case 4:
                topicsQuestion.add(10);
                topicsQuestion.add(21);
                return topicsQuestion;
            case 5:
                topicsQuestion.add(11);
                topicsQuestion.add(12);
                return topicsQuestion;
            case 6:
                topicsQuestion.add(13);
                topicsQuestion.add(14);
                topicsQuestion.add(15);
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(42);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(46);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(50);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(54);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(58);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(62);
                    }
                }
                return topicsQuestion;
            case 7:
                topicsQuestion.add(16);
                topicsQuestion.add(19);
                return topicsQuestion;
            case 8:
                topicsQuestion.add(8);
                topicsQuestion.add(9);
                topicsQuestion.add(17);
                topicsQuestion.add(18);
                return topicsQuestion;
            case 9:
                topicsQuestion.add(39);
                return topicsQuestion;
            case 10:
                topicsQuestion.add(22);
                topicsQuestion.add(25);
                topicsQuestion.add(26);
                topicsQuestion.add(28);
                topicsQuestion.add(29);
                topicsQuestion.add(30);
                topicsQuestion.add(31);
                return topicsQuestion;
            case 11:
                topicsQuestion.add(23);
                topicsQuestion.add(24);
                return topicsQuestion;
            case 12:
                topicsQuestion.add(39);
                return topicsQuestion;
            case 13:
                topicsQuestion.add(33);
                topicsQuestion.add(34);
                topicsQuestion.add(38);
                topicsQuestion.add(41);
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(43);
                        topicsQuestion.add(44);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(47);
                        topicsQuestion.add(48);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(51);
                        topicsQuestion.add(52);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(55);
                        topicsQuestion.add(56);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(59);
                        topicsQuestion.add(60);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(63);
                        topicsQuestion.add(64);
                    }
                }
                return topicsQuestion;
            case 14:
                topicsQuestion.add(35);
                return topicsQuestion;
            case 15:
                topicsQuestion.add(40);
                return topicsQuestion;
            case 16:
                topicsQuestion.add(1);
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(42);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(46);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(50);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(54);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(58);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(62);
                    }
                }
                return topicsQuestion;
            case 17:
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(43);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(47);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(51);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(55);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(59);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(63);
                    }
                }
                return topicsQuestion;
            case 18:
                for (String t : categorys) {
                    if (t.equals("A1") || t.equals("A")) {
                        topicsQuestion.add(45);
                    }
                    if (t.equals("B1") || t.equals("B")) {
                        topicsQuestion.add(49);
                    }
                    if (t.equals("C1") || t.equals("C")) {
                        topicsQuestion.add(53);
                    }
                    if (t.equals("D1") || t.equals("D")) {
                        topicsQuestion.add(57);
                    }
                    if (t.equals("C1E") || t.equals("D1E") || t.equals("BE") || t.equals("CE") || t.equals("DE")) {
                        topicsQuestion.add(61);
                    }
                    if (t.equals("T")) {
                        topicsQuestion.add(65);
                    }
                }
                return topicsQuestion;
            case 19:
                topicsQuestion.add(37);
                return topicsQuestion;

            default:
                return null;

        }


    }

}