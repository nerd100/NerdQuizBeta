package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Start extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    TextView question,timer,category,difficulty;

    Button btn1, btn2, btn3, btn4;

    ProgressBar pb;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList QuestionAndButtons;

    ArrayList<String> Cat1 = new ArrayList<>();
    ArrayList<String> Cat2 = new ArrayList<>();
    ArrayList<String> Cat3 = new ArrayList<>();

    String[] QuestionAndButtonsParts;
    String firstQuestion = "";
    String whichQuiz;
    String Category = "";
    String Difficulty = "";

    int boundEM = 2;
    int boundMH = 4;
    int rightAnswer = 0;
    int countRightAnswers=0;
    int countWrongAnswers=0;
    int countNerdIQ=0;
    int progressBarIndex = 60;
    int questionCounter = 0;
    int QuestionNumberQuiz = 30;
    int QuestionNumberKatQuiz = 10;
    int right = 0;
    int wrong = 0;

    int globalCounter = 0;
    android.os.Handler h;
    ImageView diffLogo;

    Random rand = new Random();
    Random r = new Random();
    CountDownTimer countDown;
    int ourCountDownTimer=0;
    int msfinished;
    private static final String JSON_ARRAY = "server_response";
    private JSONArray ques = null;

    float Kat1Answer = 0f;
    float Kat2Answer = 0f;
    float Kat3Answer = 0f;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizstart);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        diffLogo = (ImageView) findViewById(R.id.difflogo);
        diffLogo.setImageResource(R.drawable.easylogo);
        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        shared_preferences_editor = shared_preferences.edit();

        whichQuiz = shared_preferences.getString("Number", "Default");
        rightAnswer = r.nextInt(4);

        shared_preferences_editor.putInt("countNerdIQ",0);
        shared_preferences_editor.putInt("countRightAnswers",0);
        shared_preferences_editor.putInt("countWrongAnswers",0);
        shared_preferences_editor.apply();
        //createTimer();

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

    @Override
    public void onBackPressed() {
        countDown.cancel();

//        h.removeCallbacksAndMessages(null);
        btn1.getBackground().clearColorFilter();
        btn2.getBackground().clearColorFilter();
        btn3.getBackground().clearColorFilter();
        btn4.getBackground().clearColorFilter();
        startActivity(new Intent(Start.this, MainActivity.class));
        finish();
        super.onBackPressed();

    }



    private void createTimer() {
        timer = (TextView) findViewById(R.id.timer);
        countDown = new CountDownTimer(61000, 1000) {

            public void onTick(long millisUntilFinished) {
                pb.setProgress(progressBarIndex--);
                ourCountDownTimer = (int)(millisUntilFinished / 1000);
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
                //btn1.getBackground().clearColorFilter();
               // btn2.getBackground().clearColorFilter();
               // btn3.getBackground().clearColorFilter();
               // btn4.getBackground().clearColorFilter();
               // btn4.getBackground().clearColorFilter();

                if(whichQuiz.equals("1")) {
                    timer.setText("0");
                    globalCounter++;

                    if (globalCounter == 3) {
                        startActivity(new Intent(Start.this, Score.class));
                        finish();
                    } else {
                        reset();
                    }
                }else{
                    startActivity(new Intent(Start.this, Score.class));
                    finish();
                }
            }
        }.start();

    }


    void reset(){
        right = 0;
        wrong = 0;
        progressBarIndex = 60;
        createTimer();
        if(globalCounter == 1) {
            QuestionAndButtons = Cat2;
        }else if(globalCounter == 2){
            QuestionAndButtons = Cat3;
        }
        next();
    }

    public void extractJSON(String response) {
        String tmpKat = "";
        if(whichQuiz.equals("1")) {
             tmpKat = shared_preferences.getString("1kat", categoryList.get(0));
        }
        ArrayList<String> list = new ArrayList<String>();
        StringBuilder buffer = new StringBuilder();
        int counter = 0;
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
                if(whichQuiz.equals("1")) {
                    if (!Category.contains(tmpKat)) {
                        tmpKat = Category;
                        counter++;
                    }
                }
                if(counter == 0) {
                    Cat1.add(buffer.toString());
                }else if(counter == 1){
                    Cat2.add(buffer.toString());
                }else{
                    Cat3.add(buffer.toString());
                }
                buffer.delete(0, buffer.length());

            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public String Question(ArrayList questionList,String Difficulty) {
        String question;
        if(whichQuiz.equals("1")){
        for(int i = 0 ; i < questionList.size()-1;i++) {

            if (questionList.get(i).toString().contains(Difficulty)) {
                question = questionList.get(i).toString();
                questionList.remove(i);
                return question;
                ////////////////////////////////////////////////////////////////////Delete
            }
            /*else if (questionList.get(i).toString().contains("Medium")) {
                question = questionList.get(i).toString();
                questionList.remove(i);
                return question;

            }else if (questionList.get(i).toString().contains("Hard")) {
                question = questionList.get(i).toString();
                questionList.remove(i);
                return question;
            }*/
        }
            ////////////////////////////////////////////////////////////////////Delete
        }else{
                    question = questionList.get(0).toString();
                    questionList.remove(0);
                    return question;
            }

        //question = questionList.get(0).toString();
        return "Default";
    }

    public void next() {
        rightAnswer = r.nextInt(4);
        if(whichQuiz.equals("1")) {
            if (right - wrong >= boundMH) {
                firstQuestion = Question(QuestionAndButtons, "Hard");
                diffLogo.setImageResource(R.drawable.hardlogo);
            } else if (right - wrong >= boundEM) {
                firstQuestion = Question(QuestionAndButtons, "Medium");
                diffLogo.setImageResource(R.drawable.mediumlogo);
            } else {
                firstQuestion = Question(QuestionAndButtons, "Easy");
                diffLogo.setImageResource(R.drawable.easylogo);
            }
        }else{
            firstQuestion = Question(QuestionAndButtons, "");
        }
        QuestionAndButtonsParts = firstQuestion.split(";");
        NameButtons();
    }

    public void NameButtons() {
        question = (TextView) findViewById(R.id.question1);
        question.setText(QuestionAndButtonsParts[0]);

        category = (TextView) findViewById(R.id.cate);
        difficulty = (TextView) findViewById(R.id.diff);

        category.setText(QuestionAndButtonsParts[5]);
        difficulty.setText(QuestionAndButtonsParts[6]);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        //mix all buttons
        mixButtons(rightAnswer, QuestionAndButtonsParts[1], QuestionAndButtonsParts[2], QuestionAndButtonsParts[3], QuestionAndButtonsParts[4]);

    }


    public void mixButtons(int i, final String RA, String FA1, String FA2, String FA3) {

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


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCounter++;
                String tmpRAindex="button"+String.valueOf(indexRA+1);
                shared_preferences_editor = shared_preferences.edit();

                if (String.valueOf(getResources().getResourceName(v.getId())).contains(tmpRAindex)) {
                    right++;
                    countRightAnswers +=1;
                    countNerdIQ+=10;
                    shared_preferences_editor.putInt("countRightAnswers",countRightAnswers );
                    if(questionCounter <= QuestionNumberQuiz*3) {
                        Kat1Answer++;
                    } else if(questionCounter <= QuestionNumberQuiz*6) {
                        Kat2Answer++;
                    } else if(questionCounter <= QuestionNumberQuiz*9) {
                        Kat3Answer++;
                    }
                    shared_preferences_editor.putFloat("countRightAnswersKat1", Kat1Answer);
                    shared_preferences_editor.putFloat("countRightAnswersKat2", Kat2Answer);
                    shared_preferences_editor.putFloat("countRightAnswersKat3", Kat3Answer);
                    shared_preferences_editor.putInt("countNerdIQ",countNerdIQ );
                    shared_preferences_editor.apply();
                    v.getBackground().setColorFilter(new LightingColorFilter(Color.WHITE,Color.GREEN));
                    setBGChangeIntent(v);
                } else {
                    wrong++;
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


    public void setBGChangeIntent(final View v){
        btn1.setOnClickListener(null);
        btn2.setOnClickListener(null);
        btn3.setOnClickListener(null);
        btn4.setOnClickListener(null);
        h = new android.os.Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                //Put your conditions accordingly
                v.getBackground().clearColorFilter();
                if (QuestionAndButtons.size() > 0 && ourCountDownTimer >= 1) {
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

    private void getData(String Diff2, String Cate1, String Num, String Cate2, String Cate3){
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
            createTimer();
            if(result.equals("Failed")){
                Toast.makeText(getApplicationContext(),"No Connection to Database",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Start.this, MainActivity.class));
                finish();
            }else {
                super.onPostExecute(result);
                progressDialog.dismiss();
                extractJSON(result);
                QuestionAndButtons = Cat1;
                Toast.makeText(getApplicationContext(),String.valueOf(QuestionAndButtons.size()),Toast.LENGTH_LONG).show();
                if(whichQuiz.equals("1")) {
                    firstQuestion = Question(QuestionAndButtons, "Easy");
                }else{firstQuestion = Question(QuestionAndButtons, "");}
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