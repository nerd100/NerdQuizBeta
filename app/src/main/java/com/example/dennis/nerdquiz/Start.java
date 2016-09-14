package com.example.dennis.nerdquiz;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.icu.util.ValueIterator;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Handler;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import android.os.*;

public class Start extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    TextView question,timer,category,difficulty;

    Button btn1, btn2, btn3, btn4;

    ProgressBar pb;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList QuestionAndButtons;
    String[] QuestionAndButtonsParts;
    String firstQuestion = "";
    String whichQuiz;
    String Category = "";
    String Difficulty = "";


    int rightAnswer = 0;
    int countRightAnswers=0;
    int countWrongAnswers=0;
    int countNerdIQ=0;
    int progressBarIndex = 60;
    int questionCounter = 0;
    int QuestionNumberQuiz = 2;
    int QuestionNumberKatQuiz = 10;

    Random rand = new Random();
    Random r = new Random();
    CountDownTimer countDown;
    private static final String JSON_ARRAY = "server_response";
    private JSONArray ques = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizstart);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        shared_preferences_editor = shared_preferences.edit();
        whichQuiz = shared_preferences.getString("Number", "Default");
        rightAnswer = r.nextInt(4);
        shared_preferences_editor.putInt("countNerdIQ",0);
        shared_preferences_editor.putInt("countRightAnswers",0);
        shared_preferences_editor.putInt("countWrongAnswers",0);
        shared_preferences_editor.apply();
        createTimer();

        if(whichQuiz.equals("1")) {
            categoryList.add("Anime");
            categoryList.add("Serien");
            categoryList.add("Movies");
            categoryList.add("Games");
            categoryList.add("Assi");
            Collections.shuffle(categoryList);
            shared_preferences_editor = shared_preferences.edit();
            shared_preferences_editor.putString("1kat",categoryList.get(0));
            shared_preferences_editor.putString("2kat",categoryList.get(1));
            shared_preferences_editor.putString("3kat",categoryList.get(2));
            shared_preferences_editor.apply();
            getData("", categoryList.get(0), String.valueOf(QuestionNumberQuiz),categoryList.get(1),categoryList.get(2));

        }else if(whichQuiz.equals("0")){
            String Diff,Cate;
            Diff = shared_preferences.getString("Difficulty", "Default");
            Cate = shared_preferences.getString("Category", "Default");
            getData(Diff, Cate, String.valueOf(QuestionNumberKatQuiz),"","");
        }

    }

    private void createTimer() {
        timer = (TextView) findViewById(R.id.timer);
        countDown = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                pb.setProgress(progressBarIndex--);
                timer.setText(String.valueOf(millisUntilFinished / 1000));

                if((millisUntilFinished/1000)==59){

                    pb.getProgressDrawable().setColorFilter(
                            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if((millisUntilFinished/1000)==30){

                    pb.getProgressDrawable().setColorFilter(
                            Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if((millisUntilFinished/1000)==10){
                    pb.getProgressDrawable().setColorFilter(
                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                }

            }

            public void onFinish() {
                timer.setText("done!");
                startActivity(new Intent(Start.this, Score.class));
                finish();

            }
        }.start();

    }

    private ArrayList extractJSON(String response) {
        ArrayList<String> list = new ArrayList<String>();
        StringBuilder buffer = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);
            ques = jsonObject.getJSONArray(JSON_ARRAY);        //Hier Parsen hopefully
            for (int i = 0; i < ques.length(); i++) {
                JSONObject jsonobject = ques.getJSONObject(i);
                String Question = jsonobject.getString("Question");
                String RA = jsonobject.getString("RA");
                String FA1 = jsonobject.getString("FA1");
                String FA2 = jsonobject.getString("FA2");
                String FA3 = jsonobject.getString("FA3");
                Difficulty = jsonobject.getString("Difficulty");
                Category = jsonobject.getString("Category");   //TODO: Category and Difficulty
                buffer.append(Question + ";" + RA + ";" + FA1 + ";" + FA2 + ";" + FA3+";"+Category+";"+Difficulty);

                list.add(buffer.toString());
                buffer.delete(0, buffer.length());
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return list;
    }

    public String Question(ArrayList questionList) {
        String question;
        int i = rand.nextInt(questionList.size());
        question = questionList.get(0).toString();
        questionList.remove(0);
        return question;
    }

    public void next() {
        rightAnswer = r.nextInt(4);
        firstQuestion = Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(";");
        NameButtons();
    }

    public void NameButtons() {
        question = (TextView) findViewById(R.id.question1);
        question.setText(QuestionAndButtonsParts[0]);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        category = (TextView) findViewById(R.id.cate);
        difficulty = (TextView) findViewById(R.id.diff);
        //mix all buttons
        mixButtons(rightAnswer, QuestionAndButtonsParts[1], QuestionAndButtonsParts[2], QuestionAndButtonsParts[3], QuestionAndButtonsParts[4], QuestionAndButtonsParts[5], QuestionAndButtonsParts[6]);

    }


    public void mixButtons(int i, final String RA, String FA1, String FA2, String FA3, String categoryText,String difficultyText) {

        ArrayList<String> tmpShuffle=new ArrayList<>();
        tmpShuffle.add(RA);
        tmpShuffle.add(FA1);
        tmpShuffle.add(FA2);
        tmpShuffle.add(FA3);

        Collections.shuffle(tmpShuffle);
        final int indexRA=tmpShuffle.indexOf(RA);

        btn1.setText(tmpShuffle.get(0));
        btn2.setText(tmpShuffle.get(1));
        btn3.setText(tmpShuffle.get(2));
        btn4.setText(tmpShuffle.get(3));
        category.setText(categoryText);
        difficulty.setText(difficultyText);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                questionCounter++;
                String tmpRAindex="button"+String.valueOf(indexRA+1);
                shared_preferences_editor = shared_preferences.edit();

                if (String.valueOf(getResources().getResourceName(v.getId())).contains(tmpRAindex)) {
                    countRightAnswers +=1;
                    countNerdIQ+=10;
                    shared_preferences_editor.putInt("countRightAnswers",countRightAnswers );
                    shared_preferences_editor.putInt("countNerdIQ",countNerdIQ );
                    shared_preferences_editor.apply();
                    v.getBackground().setColorFilter(new LightingColorFilter(Color.WHITE,Color.GREEN));
                    setBGChangeIntent(v);
                } else {
                    countWrongAnswers+=1;
                    shared_preferences_editor.putInt("countWrongAnswers",countWrongAnswers );
                    shared_preferences_editor.apply();
                    v.getBackground().setColorFilter(new LightingColorFilter(Color.WHITE,Color.RED));
                    setBGChangeIntent(v);
                }
            }
        };

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
    }


    private void setBGChangeIntent(final View v){

        android.os.Handler h = new android.os.Handler();
        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                //Put your conditions accordingly
                v.getBackground().clearColorFilter();
                if (QuestionAndButtons.size() > 0) {
                    next();
                } else {
                    shared_preferences_editor = shared_preferences.edit();
                    shared_preferences_editor.putInt("questionCounter",questionCounter);
                    shared_preferences_editor.apply();
                    countDown.cancel();
                    startActivity(new Intent(Start.this, Score.class));
                    finish();
                }
            }
        }, 1000);

    }

    private void getData(String Diff2, String Cate1, String Num,String Cate2, String Cate3){
        String Difficulty,Category,Category2,Category3,Number;
        Difficulty = Diff2;
        Category = Cate1;
        Category2 = Cate2;
        Category3 = Cate3;
        Number = Num;
        Toast.makeText(getApplicationContext(),Category,Toast.LENGTH_LONG).show();

     class GetDataJSON extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Start.this, "Downloading Quiz", "Wait....", true);
        }

        protected String doInBackground(String... params) {
            String JSON_STRING;
            String result = "";

            try {
                String json_url = "http://quizdb.net23.net/json_get_data.php";
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                if(whichQuiz.equals("1")){
                    String data =
                            URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                                    URLEncoder.encode("Category2", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                                    URLEncoder.encode("Category3", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&" +
                                    URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                    URLEncoder.encode("Number", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }else if(whichQuiz.equals("0")) {
                    String data =
                            URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                                    URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                    URLEncoder.encode("Number", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString();

                return result ;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Failed";

        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Failed")){
                Toast.makeText(getApplicationContext(),"No Connection to Database",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Start.this, MainActivity.class));
                finish();
            }else {
                super.onPostExecute(result);
                progressDialog.dismiss();
                QuestionAndButtons = extractJSON(result);
                firstQuestion = Question(QuestionAndButtons);
                QuestionAndButtonsParts = firstQuestion.split(";");

                NameButtons();
            }
        }
    }
        GetDataJSON asyncObject = new GetDataJSON();
        asyncObject.execute(Category,Difficulty,Number,Category2,Category3);
        //asyncObject.cancel(true);

    }
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here
        }
    };
}