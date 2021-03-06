package com.example.dennis.nerdquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SirLui on 09.09.2016.
 */
public class Score extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button retry;

    TextView rightAnswer,wrongAnswer,points,tmpiq,rank,switchText;
    ImageView tmpmed;
    String getWhichQuiz;
    String title;
    StringBuilder buffer = new StringBuilder();
    ArrayList<String> ranksTitle = new ArrayList<>();
    ArrayList<String> ranksValue = new ArrayList<>();
    int questionCounter;
    int rightAns;

    Map<String,Integer> category= new HashMap<>();
    Map<String,String> difficulty= new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        retry = (Button) findViewById(R.id.retry);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        getWhichQuiz = shared_preferences.getString("Number", "Default");

        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        wrongAnswer = (TextView) findViewById(R.id.wrongAnswer);
        points = (TextView) findViewById(R.id.points);
     //   tmpiq = (TextView) findViewById(R.id.tmpiq);
        tmpmed = (ImageView) findViewById(R.id.tmpmed);
        rank = (TextView) findViewById(R.id.title);
        switchText = (TextView) findViewById(R.id.switchText);

        if(getWhichQuiz.equals("1")){
            switchText.setText("Dein Rang:");
            int score = shared_preferences.getInt("countNerdIQ",0);
            buffer.append("0,Anfänger,50,CasualNerd,100,KonsolenFan,200,ComputerUser,300,Programmer," +
                    "400,Allrounder,500,Senpai,600,NerdGod,700,HardcoreGamer,800,All-Star,1000,NerdQuizDeveloper,100000,NerdQuizDeveloper");
            //Toast.makeText(getApplicationContext(),String.valueOf(score),Toast.LENGTH_LONG).show();
            rankSplit(buffer.toString());
            title = getNerdTitle(score);
            tmpmed.setVisibility(View.INVISIBLE);

            //Achievements
            int tmpc=shared_preferences.getInt("counterQuiz",0);
            tmpc+=1;
            shared_preferences_editor=shared_preferences.edit();
            shared_preferences_editor.putInt("counterQuiz",tmpc).apply();
            if(tmpc<=100){
            switch(tmpc){
                case 5:
                    shared_preferences_editor.putBoolean("A1",true).apply();
                    break;
                case 10:
                    shared_preferences_editor.putBoolean("A2",true).apply();
                    break;
                case 25:
                    shared_preferences_editor.putBoolean("A3",true).apply();
                    break;
                case 100:
                    shared_preferences_editor.putBoolean("A4",true).apply();
                    break;
                }
            }


            switch(title){
                case "Anfänger":
                    shared_preferences_editor.putBoolean("A13",true).apply();
                    break;
                case "CasualNerd":
                    setAchievements(1);
                    break;
                case "KonsolenFan":
                    setAchievements(2);
                    break;
                case "ComputerUser":
                    setAchievements(3);
                    break;
                case "Programmer":
                    setAchievements(4);
                    break;
                case "Allrounder":
                    setAchievements(5);
                    break;
                case "Senpai":
                    setAchievements(6);
                    break;
                case "NerdGod":
                    setAchievements(7);
                    break;
                case "HardcoreGamer":
                    setAchievements(8);
                    break;
                case "All-Star":
                    setAchievements(9);
                    break;
                case "NerdQuizDeveloper":
                    setAchievements(10);
                    break;
            }

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
            kate1 += shared_preferences.getFloat("countRightAnswersKat1",0f)/10;
            kate2 += shared_preferences.getFloat("countRightAnswersKat2",0f)/10;
            kate3 += shared_preferences.getFloat("countRightAnswersKat3",0f)/10;
            shared_preferences_editor = shared_preferences.edit();
            shared_preferences_editor.putFloat(kat1, kate1);
            shared_preferences_editor.putFloat(kat2, kate2);
            shared_preferences_editor.putFloat(kat3, kate3);
            shared_preferences_editor.putString("Rang", title);
            shared_preferences_editor.apply();

            rank.setText(title);
            compareScore();

        }else if(getWhichQuiz.equals("0")){

            shared_preferences_editor = shared_preferences.edit();
            String cate = shared_preferences.getString("Category","Default");
            String diff = shared_preferences.getString("Difficulty","Default");
            rank.setText("");
            //switchText.setGravity(Gravity.CENTER_VERTICAL);
            switchText.setText("Deine Medaille:");
            tmpmed.setVisibility(View.VISIBLE);
            category.put("Anime",1);
            category.put("Serien",2);
            category.put("Movies",3);
            category.put("Games",4);
            category.put("MINT",5);
            difficulty.put("Easy","e");
            difficulty.put("Medium","m");
            difficulty.put("Hard","h");

            questionCounter = shared_preferences.getInt("questionCounter", 0);
            rightAns = shared_preferences.getInt("countRightAnswers", 0);

            int i = category.get(cate);
            String j = difficulty.get(diff);
            if (rightAns > 20){
                tmpmed.setImageResource(R.drawable.diamandm);
                compareMedaille(4,"k"+String.valueOf(i)+j);
                shared_preferences_editor.putBoolean("A12",true).apply();
            }else if(rightAns>15){
                tmpmed.setImageResource(R.drawable.goldm);
                shared_preferences_editor.putBoolean("A11",true).apply();
                compareMedaille(3,"k"+String.valueOf(i)+j);
            }else if(rightAns>10){
                tmpmed.setImageResource(R.drawable.silberm);
                shared_preferences_editor.putBoolean("A10",true).apply();
                compareMedaille(2,"k"+String.valueOf(i)+j);
            }else if(rightAns>5){
                tmpmed.setImageResource(R.drawable.bronzem);
                shared_preferences_editor.putBoolean("A9",true).apply();
                compareMedaille(1,"k"+String.valueOf(i)+j);
            }

            shared_preferences_editor.apply();
            //tmpmed.setImageResource(R.drawable.diamandm);

    }

        rightAnswer.setText(String.valueOf(shared_preferences.getInt("countRightAnswers", 0)));
        wrongAnswer.setText(String.valueOf(shared_preferences.getInt("countWrongAnswers", 0)));
        points.setText(String.valueOf(shared_preferences.getInt("countNerdIQ", 0)));

        retry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    retry.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    String quiz = "";
                    quiz = shared_preferences.getString("Number","0");
                    if(quiz.equals("1")) {
                        shared_preferences_editor.putString("Number", "1");
                        shared_preferences_editor.apply();
                    }else if(quiz.equals("0")) {
                        shared_preferences_editor.putString("Number", "0");
                        shared_preferences_editor.apply();
                    }
                    startActivity(new Intent(Score.this, Start.class));
                    finish();
                }
                return true;
            }
        });


    }

    public void setAchievements(int numA){
        for(int i=0; i<=numA; i++ ){
            int x=i+13;
            String tmop="A"+String.valueOf(x);
            shared_preferences_editor.putBoolean(tmop,true).apply();
        }
    }

    public int readHighscore() {
        return shared_preferences.getInt("HIGHSCORE", 0);
    }
    public int readMedaille(String katMed) {
        return shared_preferences.getInt(katMed, 0);
    }

    public void writeHighscore(int highscore) {
        shared_preferences_editor = shared_preferences.edit();
        shared_preferences_editor.putInt("HIGHSCORE", highscore);
        shared_preferences_editor.apply();

    }
    public void writeMedaille(int highscore,String katMed) {
        shared_preferences_editor = shared_preferences.edit();
        shared_preferences_editor.putInt(katMed, highscore);
        shared_preferences_editor.apply();

    }

    public void compareScore() {
        if (shared_preferences.getInt("countNerdIQ",0) > readHighscore()) {
            writeHighscore(shared_preferences.getInt("countNerdIQ",0));
            String HashedDeviceID = shared_preferences.getString("DeviceId","Default");
            String Name = shared_preferences.getString("username","Default");
            String Score = String.valueOf(shared_preferences.getInt("countNerdIQ",0));
            String method = "InsertScore";
            BackgroundTaskScore backgroundTask = new BackgroundTaskScore(this);
            backgroundTask.execute(method,HashedDeviceID,Name,Score);
        }
    }
    public void compareMedaille(int currentMed,String katMed) {
        if (currentMed > readMedaille(katMed)) {
            writeMedaille(currentMed,katMed);
        }
    }

    public void rankSplit(String rankArray){
        String[] stringCutter;
        String score;
        String title;
        stringCutter = rankArray.split(",");

        for (int i = 0 ; i < stringCutter.length ; i+=2){
            score = stringCutter[i];
            title = stringCutter[i+1];
            ranksValue.add(score);
            ranksTitle.add(title);
        }

    }
    public String getNerdTitle(int Score){              //r
        for(int i = 0; i < ranksValue.size(); i++){
            if(Score <= Integer.parseInt(ranksValue.get(i))){
                return ranksTitle.get(i);
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
        if(kat.equals("MINT")){
            return "5";
        }else{
            return "0";
        }

    }

    public void onBackPressed() {

        startActivity(new Intent(Score.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(Score.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
