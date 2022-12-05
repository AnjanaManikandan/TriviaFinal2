package com.example.triviafinal2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.triviafinal2.QuestionHelp.*;

import java.util.ArrayList;


public class DatabaseSQL extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TriviaQuiz.db";
    private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;

    public DatabaseSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("How many bones are there in the human body?",
                "206", "112", "307", 1, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("How many points are a touchdown worth?",
                "2", "6", "4", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q2);
        Question q3 = new Question("Which fictional city is the home of Batman?",
                "Harlem City", "Batman City", "Gotham City", 3, Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Harry, Niall, Louis, Liam, and Zayn were in a band. What was it called?",
                "3 Directions", "No Direction", "One Direction", 3, Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Who is the author of the Harry Potter series?",
                "J.K. Rowling", "Julia Stone", "Harper Lee", 1, Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Fe is the chemical symbol for which element?",
                "Gold", "Copper", "Iron", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q6);

        Question q7 = new Question("What temperature (in Fahrenheit) does water freeze at?",
                "0 degrees", "32 degrees", "12 degrees", 2, Question.DIFFICULTY_EASY);
        addQuestion(q7);
        Question q8 = new Question("Which country occupies half of South America’s western coast?",
                "Chile", "Costa Rica", "Brazil", 1, Question.DIFFICULTY_HARD);
        addQuestion(q8);
        Question q9 = new Question("Which U.S. state is known as “America’s Dairyland”?",
                "New Jersey", "California", "Wisconsin", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("What language is the most popularly spoken worldwide?",
                "Russian", "Chinese", "English", 2, Question.DIFFICULTY_HARD);
        addQuestion(q10);

        Question q11 = new Question("How many inches are in a yard?",
                "36", "12", "24", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q11);

        Question q12 = new Question("In what country was Elon Musk born? ",
                "South Africa", "Ireland", "South Korea", 2, Question.DIFFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("What country has the most islands?",
                "Russian", "Sweden", "English", 2, Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("How many dots appear on a pair of dice?",
                "56", "42", "24", 2, Question.DIFFICULTY_HARD);
        addQuestion(q14);
        Question q15 = new Question( "what US state is the country's busiest airport located?",
                "Florida", "Georgia", "Texas", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q15);
        Question q16 = new Question("Where is the strongest human muscle located?",
                "Arm", "Legs", "Jaw", 2, Question.DIFFICULTY_HARD);
        addQuestion(q16);
        Question q17 = new Question("On what continent would you find the city of Baku?",
                "Asia", "Europe", "North America", 2, Question.DIFFICULTY_HARD);
        addQuestion(q17);
        Question q18 = new Question("What colors is the flag of the United Nations?",
                "Green & Red", "Yellow & Red", "Blue & white", 2, Question.DIFFICULTY_HARD);
        addQuestion(q18);
        Question q19 = new Question("Simone Biles is famous for her skill in what sport?",
                "Soccer", "Gymnastics", "Fence", 2, Question.DIFFICULTY_EASY);
        addQuestion(q19);
        Question q20 = new Question("What sporting event has a strict dress code of all white?",
                "Olympic Games", "Wimbleton", "super Bowl", 2, Question.DIFFICULTY_HARD);
        addQuestion(q20);
    }


    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}