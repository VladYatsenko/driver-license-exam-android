package com.android.testdai.application.data.local.db;

public class DbSchema {

    public static final class QuestionTable{

        static final String NAME = "question";

        public static final class Cols{
            public static final String ID = "_id";
            public static final String TEXTQUESTION = "text_question";
            public static final String IMAGESOURCE = "source_image";
            public static final String TOPIC = "fk_topic";
        }
    }

    public static final class AnswerTable{

        static final String NAME = "answer";

        public static final class Cols{
            public static final String TEXTANSWER = "text_answer";
            public static final String ANSWERTRUE = "correct";
            public static final String FKQUESTION = "fk_question";
        }
    }
}
