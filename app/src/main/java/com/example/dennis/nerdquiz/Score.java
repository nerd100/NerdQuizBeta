package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SirLui on 09.09.2016.
 */
public class Score extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    TextView rightAnswer,wrongAnswer,points,tmpiq,rank,switchText;
    ImageView tmpmed;
    String getWhichQuiz;
    String title;
    StringBuilder buffer = new StringBuilder();
    ArrayList<String> ranks = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        getWhichQuiz = shared_preferences.getString("Number", "Default");

        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        wrongAnswer = (TextView) findViewById(R.id.wrongAnswer);
        points = (TextView) findViewById(R.id.points);
        tmpiq = (TextView) findViewById(R.id.tmpiq);
        tmpmed = (ImageView) findViewById(R.id.tmpmed);
        rank = (TextView) findViewById(R.id.title);
        switchText = (TextView) findViewById(R.id.switchText);

        if(getWhichQuiz.equals("1")){
            switchText.setText("Dein Rang:");
            int score = shared_preferences.getInt("countNerdIQ",0);
            buffer.append("0,Anfänger,50,CasualNerd,100,KonsolenFan,200,ComputerUser" +
                    ",300,Programmer,400,Allrounder,500,Nerd,600,Geek,700,HardcoreGamer," +
                    "800,All-Star,1000,NerdQuizDeveloper");
            rankSplit(buffer.toString());
            title = getNerdTitle(score);

            shared_preferences_editor = shared_preferences.edit();
            shared_preferences_editor.putString("Rang", title);
            shared_preferences_editor.apply();

            rank.setText(title);
            compareScore();

        }else if(getWhichQuiz.equals("0")){
            switchText.setText("Deine Medaille:");
            tmpmed.setImageResource(R.drawable.diamandm);
        }

        rightAnswer.setText(String.valueOf(shared_preferences.getInt("countRightAnswers", 0)));
        wrongAnswer.setText(String.valueOf(shared_preferences.getInt("countWrongAnswers", 0)));
        points.setText(String.valueOf(shared_preferences.getInt("countNerdIQ", 0)));

    }

    public int readHighscore() {
        return shared_preferences.getInt("HIGHSCORE", 0);
    }

    public void writeHighscore(int highscore) {
        shared_preferences_editor = shared_preferences.edit();
        shared_preferences_editor.putInt("HIGHSCORE", highscore);
        shared_preferences_editor.apply();

    }

    public void compareScore() {
        if (shared_preferences.getInt("countNerdIQ",0) > readHighscore()) {
            writeHighscore(shared_preferences.getInt("countNerdIQ",0));
        }
    }

    public void rankSplit(String rankArray){
        String[] stringCutter;
        String score;
        String title;
        stringCutter = rankArray.split(",");

        for (int i = 0 ; i < stringCutter.length/2 ; i+=2){
            score = stringCutter[i];
            title = stringCutter[i+1];
            ranks.add(score);
            ranks.add(title);
        }

    }
    public String getNerdTitle(int Score){
        for(int i = 0; i < ranks.size()/2; i+=2){
            if(Score < Integer.parseInt(ranks.get(i+2))){
                return ranks.get(i+1);
            }
        }
        return "no Rank";
    }
}
