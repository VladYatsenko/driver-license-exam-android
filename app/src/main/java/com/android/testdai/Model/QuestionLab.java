package com.android.testdai.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Parcelable;

import com.android.testdai.Database.AnswerCursorWrapper;
import com.android.testdai.Database.QuestionBaseHelper;
import com.android.testdai.Database.QuestionCursorWrapper;
import com.android.testdai.Database.QuestionDbSchema;
import com.android.testdai.Model.Question.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionLab extends ArrayList<Parcelable> {

    private static QuestionLab sQuestionLab;
    private Context mContext;
    private List<Question> mQuestions;
    private List<Answer> mAnswer;
    private QuestionBaseHelper mDatabase;


    public QuestionLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new QuestionBaseHelper(mContext);
        try {
            mDatabase.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            mDatabase.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        //mDatabase = mDatabase.getReadableDatabase();//new QuestionBaseHelper(mContext).getWritableDatabase();
        //Log.i("SQLite", mDatabase.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null).toString());
    }

    public static QuestionLab get(Context context) {
        if(sQuestionLab == null){
            sQuestionLab = new QuestionLab(context);
        }
        return sQuestionLab;
    }



    //request to DB
    private QuestionCursorWrapper queryQuestion(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                QuestionDbSchema.QuestionTable.NAME,
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
    private AnswerCursorWrapper queryAnswer(String[] whereArgs){
        Cursor cursor = mDatabase.query(
                QuestionDbSchema.AnswerTable.NAME,
                null,
                QuestionDbSchema.AnswerTable.Cols.FKQUESTION +"=?",
                whereArgs,
                null,
                null,
                null
        );
        return new AnswerCursorWrapper(cursor);
    }

    //request to DB
    private Cursor queryImageSource(){
        Cursor cursor = mDatabase.query(
                QuestionDbSchema.QuestionTable.NAME,
                new String[]{QuestionDbSchema.QuestionTable.Cols.IMAGESOURCE},
                QuestionDbSchema.QuestionTable.Cols.IMAGESOURCE +" != ?",
                new String[]{""},
                null,
                null,
                null
        );
        return cursor;
    }

    public List<String> getImageSource(){
        List<String> mImageSource = new ArrayList<>();

        Cursor c = queryImageSource();
        if (c.moveToFirst()) {
            do {
                mImageSource.add(c.getString(0));
            } while (c.moveToNext());
        }

        return mImageSource;
    }

    public Question getQuestion(int id){
        for (Question question: mQuestions){
            if(id == question.getId()){
                return question;
            }
        }
        return null;
    }

    public List<Answer> getAnswers(int id){
        Question question = getQuestion(id);
        List <Answer> answers = question.getAnswer();
        return answers;
    }

    //cursor to List
    public List<Question> getQuestions(String category){
        mQuestions = new ArrayList<>();
        for(int i=0; i<20; i++) {
            String questionsNumber = String.valueOf(randomQuestion(i, category));//random
            QuestionCursorWrapper cursor = queryQuestion(QuestionDbSchema.QuestionTable.Cols.ID +"=?", new String[]{questionsNumber});
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    mQuestions.add(cursor.getQuestion(getAnswers(new String[]{questionsNumber})));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
        return mQuestions;
    }

    //cursor to List
    public List<Answer> getAnswers(String[] whereArgs){
        mAnswer = new ArrayList<>();

        AnswerCursorWrapper cursor = queryAnswer(whereArgs);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                mAnswer.add(cursor.getAnswer());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return mAnswer;
    }

    public int random(int questionNumber, String category){

        int[][] range = {{1, 32},{33, 67} ,{68, 81}, {82, 102}, {103, 117}, {118, 131}, {132, 139}, {140, 207} };
        int result;
        //Log.i("SQLite", String.valueOf(range[i][0]));
        //Log.i("SQLite", String.valueOf(range[i][1]));
        Random rnd = new Random(System.currentTimeMillis());
        result =  range[questionNumber][0]+rnd.nextInt(range[questionNumber][1]-range[questionNumber][0]);
        return result;
    }

    public int randomQuestion(int questionNumber, String category){
        List <Integer> topicsQuestion = listTopic(questionNumber, category);

        Random rnd = new Random(System.currentTimeMillis());
        int index =  rnd.nextInt(topicsQuestion.size());
        int numberQuestion = numberQuestion(topicsQuestion.get(index));

        return numberQuestion;
    }

    public List<Integer> listTopic(int questionNumber, String category){
        List <Integer> topicsQuestion = new ArrayList<>();
        String[] categorys = category.split(";");
        switch (questionNumber){
            case 0:
                topicsQuestion.add(2);
                topicsQuestion.add(4);
                topicsQuestion.add(5);
                topicsQuestion.add(6);
                topicsQuestion.add(7);
                topicsQuestion.add(20);
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(42);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(46);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(50);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(54);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(58);
                    }
                    if(t.equals("T")){
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
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(42);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(46);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(50);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(54);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(58);
                    }
                    if(t.equals("T")){
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
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(43);
                        topicsQuestion.add(44);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(47);
                        topicsQuestion.add(48);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(51);
                        topicsQuestion.add(52);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(55);
                        topicsQuestion.add(56);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(59);
                        topicsQuestion.add(60);
                    }
                    if(t.equals("T")){
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
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(42);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(46);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(50);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(54);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(58);
                    }
                    if(t.equals("T")){
                        topicsQuestion.add(62);
                    }
                }
                return topicsQuestion;
            case 17:
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(43);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(47);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(51);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(55);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(59);
                    }
                    if(t.equals("T")){
                        topicsQuestion.add(63);
                    }
                }
                return topicsQuestion;
            case 18:
                for(String t: categorys){
                    if(t.equals("A1")||t.equals("A")){
                        topicsQuestion.add(45);
                    }
                    if(t.equals("B1")||t.equals("B")){
                        topicsQuestion.add(49);
                    }
                    if(t.equals("C1")||t.equals("C")){
                        topicsQuestion.add(53);
                    }
                    if(t.equals("D1")||t.equals("D")){
                        topicsQuestion.add(57);
                    }
                    if(t.equals("C1E")||t.equals("D1E")||t.equals("BE")||t.equals("CE")||t.equals("DE")){
                        topicsQuestion.add(61);
                    }
                    if(t.equals("T")){
                        topicsQuestion.add(65);
                    }
                }
                return topicsQuestion;
            case 19:
                topicsQuestion.add(37);
                return topicsQuestion;

            default: return null;

        }


    }

    private int numberQuestion(int topicId){
        Random rnd = new Random(System.currentTimeMillis());
        int number = 0; //min + rnd.nextInt(max - min + 1);
        int range [] = new int[2];

        int Range [][] = {{1,35}, {36,69}, {70,85}, {86,106}, {107,121}, {122,135},{136,143},{144,226},{227,233},{234,292},
                {293,353}, {354,393}, {394,431}, {432,444}, {445,490}, {491,578}, {579,610}, {611,711}, {712,719}, {720,735},
                {736,761}, {762,784}, {785,792}, {793,798}, {799,819}, {820,831}, {832,839}, {840,850}, {851,864}, {865,872},
                {873,873}, {874,886}, {887,906}, {907,914}, {915,1248}, {1249,1282}, {1283,1322}, {1323,1330}, {1331,1364}, {1365, 1365},
                {1383,1389}, {1390,1420}, {1421,1432}, {1433,1452}, {1453,1488}, {1489,1505}, {1506,1515}, {1516,1525}, {1526, 1532}, {1533,1619},
                {1620,1674}, {1675,1686}, {1687,1729}, {1730,1796}, {1797,1842}, {1843,1878}, {1879,1907}, {1908,1928}, {1929, 1933}, {1934,1936},
                {1937,1952}, {1953,1970}, {1971,1977}, {1978, 1993}, {1994,2005}
        };

        return Range[topicId-1][0] + rnd.nextInt(Range[topicId-1][1]-Range[topicId-1][0]+1);

        //return 0;
    }


}