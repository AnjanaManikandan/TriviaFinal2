package com.example.triviafinal2;

import static com.example.triviafinal2.MainActivity.SHARED_PREFS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FinalScreenActivity extends AppCompatActivity     {
        public static final String SHARED_PREFS = "sharedPrefs";
        public static final String KEY_HIGHSCORE = "keyHighscore";
        private static final int REQUEST_CODE_QUIZ = 1;

        private TextView textViewHighscore;
        private Spinner spinnerDifficulty;
        private int highscore;
        private MediaPlayer maintitle2;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.final_screen);

            maintitle2 = MediaPlayer.create(this,R.raw.maintitle2);
            maintitle2.start();


            textViewHighscore = findViewById(R.id.highscore);
            spinnerDifficulty = findViewById(R.id.spinner_difficulty);

            String[] difficultyLevels = Question.getAllDifficultyLevels();
            ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultyLevels);

            adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinnerDifficulty.setAdapter(adapterDifficulty);
            loadHighscore();

            Button buttonStartQuiz = findViewById(R.id.startButton);
            buttonStartQuiz.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startQuiz();
                }
            });
        }

        private void startQuiz() {
            maintitle2.stop();
            String difficulty = spinnerDifficulty.getSelectedItem().toString();

            Intent intent = new Intent(FinalScreenActivity.this, QuizActivity.class);
            startActivityForResult(intent, REQUEST_CODE_QUIZ);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE_QUIZ) {
                if (resultCode == RESULT_OK) {
                    int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                    if (score > highscore) {
                        updateHighscore(score);
                    }
                }
            }
            Button buttonStartQuiz = findViewById(R.id.startButton);
            buttonStartQuiz.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startQuiz();
                }
            });
        }

        private void loadHighscore() {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            highscore = prefs.getInt(KEY_HIGHSCORE, 0);
            textViewHighscore.setText(highscore+" ");
        }

        private void updateHighscore(int highscoreNew) {
            highscore = highscoreNew;
            textViewHighscore.setText(highscore+" ");

            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_HIGHSCORE, highscore);
            editor.apply();
        }
}