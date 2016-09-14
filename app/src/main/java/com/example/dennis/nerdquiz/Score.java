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
    int questionCounter;
    int rightAns;

    Map<String,Integer> category= new HashMap<>();

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

            String kat1 = "";
            String kat2 = "";
            String kat3 = "";

            float kate1,kate2,kate3;

            kat1 = whichKat(shared_preferences.getString("1kat","Default"));
            kat2 = whichKat(shared_preferences.getString("2kat","Default"));
            kat3 = whichKat(shared_preferences.getString("3kat","Default"));

            kate1=shared_preferences.getFloat(kat1,0f);
            kate2=shared_preferences.getFloat(kat2,0f);
            kate3=shared_preferences.getFloat(kat3,0f);

            shared_preferences_editor = shared_preferences.edit();
            shared_preferences_editor.putFloat(kat1, kate1+1);
            shared_preferences_editor.putFloat(kat2, kate2+1);
            shared_preferences_editor.putFloat(kat3, kate3+1);
            shared_preferences_editor.putString("Rang", title);
            shared_preferences_editor.apply();

            rank.setText(title);
            compareScore();

        }else if(getWhichQuiz.equals("0")){
            shared_preferences_editor = shared_preferences.edit();
            String cate = shared_preferences.getString("Category","Default");
            switchText.setText("Deine Medaille:");

            category.put("Anime",1);
            category.put("Serien",2);
            category.put("Movies",3);
            category.put("Games",4);
            category.put("League of Legends",5);
            category.put("Assi",6);

            questionCounter = shared_preferences.getInt("questionCounter", 0);
            rightAns = shared_preferences.getInt("countRightAnswers", 0);
            int i = category.get(cate);
            if (questionCounter-rightAns > questionCounter/2){
                tmpmed.setImageResource(R.drawable.bronzem);
                shared_preferences_editor.putInt("k"+String.valueOf(i)+"m", 1);
            }else if(questionCounter-rightAns < questionCounter/2){
                tmpmed.setImageResource(R.drawable.silberm);
                shared_preferences_editor.putInt("k"+String.valueOf(i)+"m", 2);
            }
            shared_preferences_editor.apply();
            //tmpmed.setImageResource(R.drawable.diamandm);
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

    public String whichKat(String kat){
        if(kat.equals("Anime")){
            return "1";
        }
        if(kat.equals("Serien")){
            return "2";
        }
        if(kat.equals("Movies")){
            return "3";
        }
        if(kat.equals("Games")){
            return "4";
        }
        if(kat.equals("Assi")){
            return "5";
        }else{
            return "0";
        }

    }
}
