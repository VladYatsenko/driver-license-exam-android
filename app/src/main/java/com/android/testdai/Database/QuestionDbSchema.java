package com.android.testdai.Database;

public class QuestionDbSchema {
    public static final class QuestionTable{
        public static final String NAME = "question";

        public static final class Cols{
            public static final String ID = "_id";
            public static final String TEXTQUESTION = "name_question";
            public static final String IMAGESOURCE = "source_image";
        }
    }

    public static final class AnswerTable{
        public static final String NAME = "answer";

        public static final class Cols{
            public static final String TEXTANSWER = "name_answer";
            public static final String ANSWERTRUE = "correct";
            public static final String FKQUESTION = "fk_question";
        }
    }
}
