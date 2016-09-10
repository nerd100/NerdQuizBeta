package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SirLui on 09.09.2016.
 */
public class Score extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    TextView rightAnswer,wrongAnswer,points;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        wrongAnswer = (TextView) findViewById(R.id.wrongAnswer);
        points = (TextView) findViewById(R.id.points);

        rightAnswer.setText(String.valueOf(shared_preferences.getInt("countRightAnswers",0)));
        wrongAnswer.setText(String.valueOf(shared_preferences.getInt("countWrongAnswers",0)));
        points.setText(String.valueOf(shared_preferences.getInt("countNerdIQ",0)));

        compareScore();
    }

    public int readHighscore() {
        return shared_preferences.getInt("HIGHSCORE", 0);
    }

    public void writeHighscore(int highscore) {
        shared_preferences_editor = shared_preferences.edit();
        shared_preferences_editor.putInt("HIGHSCORE", highscore);
        shared_preferences_editor.commit();

    }

    public void compareScore() {
        if (shared_preferences.getInt("countNerdIQ",0) > readHighscore()) {
            writeHighscore(shared_preferences.getInt("countNerdIQ",0));
        }
    }
}
