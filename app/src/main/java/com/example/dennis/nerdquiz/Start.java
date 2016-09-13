package com.example.dennis.nerdquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView question;
    Button btn1, btn2, btn3, btn4;
    ArrayList QuestionAndButtons;
    String[] QuestionAndButtonsParts;
    String firstQuestion = "";
    String whichQuiz;
    int rightAnswer = 0;
    Random rand = new Random();
    Random r = new Random();

    int countRightAnswers=0;
    int countWrongAnswers=0;
    int countNerdIQ=0;


    private static final String JSON_ARRAY = "server_response";
    private JSONArray ques = null;

    int QuestionNumber = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        shared_preferences_editor = shared_preferences.edit();
        whichQuiz = shared_preferences.getString("Number", "Default");
        rightAnswer = r.nextInt(4);
        shared_preferences_editor.putInt("countNerdIQ",0);
        shared_preferences_editor.putInt("countRightAnswers",0);
        shared_preferences_editor.putInt("countWrongAnswers",0);
        shared_preferences_editor.apply();

        if(whichQuiz.equals("1")) {
            getData("", "", String.valueOf(QuestionNumber));
        }else if(whichQuiz.equals("0")){
            String Diff,Cate;
            Diff = shared_preferences.getString("Difficulty", "Default");
            Cate = shared_preferences.getString("Category", "Default");
            getData(Diff, Cate, String.valueOf(QuestionNumber));
        }

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
                buffer.append(Question+";"+RA+";"+FA1+";"+FA2+";"+FA3);
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
        question = questionList.get(i).toString();
        questionList.remove(i);
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
        final int test12=tmpShuffle.indexOf(RA);

     //   Toast.makeText(getApplicationContext(), String.valueOf(test12),Toast.LENGTH_LONG).show();

        btn1.setText(tmpShuffle.get(0));
        btn2.setText(tmpShuffle.get(1));
        btn3.setText(tmpShuffle.get(2));
        btn4.setText(tmpShuffle.get(3));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpRAindex="button"+String.valueOf(test12+1);
                shared_preferences_editor = shared_preferences.edit();

                //Toast.makeText(getApplicationContext(),String.valueOf(tmpRAindex),Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), String.valueOf(tmpRAindex),Toast.LENGTH_LONG).show();
                if (v.toString().contains(tmpRAindex)) {
                    //v.setBackgroundColor(Color.GREEN);
                    countRightAnswers +=1;
                    countNerdIQ+=10;
                    shared_preferences_editor.putInt("countRightAnswers",countRightAnswers );
                    shared_preferences_editor.putInt("countNerdIQ",countNerdIQ );
                    shared_preferences_editor.apply();
                    btn1.setBackgroundResource(R.drawable.silberm);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn1.setBackgroundResource(R.drawable.button);
                        }
                    }, 1000);;
                    if (QuestionAndButtons.size() > 0) {
                        next();
                    } else {
                        finish();
                        startActivity(new Intent(Start.this, Score.class));
                    }
                } else {
                    //v.setBackgroundColor(Color.RED);
                    countWrongAnswers+=1;
                    shared_preferences_editor.putInt("countWrongAnswers",countWrongAnswers );
                    shared_preferences_editor.apply();
                    if (QuestionAndButtons.size() > 0) {
                        next();
                    } else {
                        startActivity(new Intent(Start.this, Score.class));
                        finish();
                    }
                }
            }
        };

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
    }

    private void getData(String Diff2,String Cate2,String Num){
        String Difficulty,Category,Number;
        Difficulty = Diff2;
        Category = Cate2;
        Number = Num;
        Toast.makeText(getApplicationContext(),Category,Toast.LENGTH_LONG).show();
     class GetDataJSON extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Start.this, "Downloading Quiz", "Wait....", true);
        }

        protected String doInBackground(String... params) {
            //Toast.makeText(getApplicationContext(),params[1],Toast.LENGTH_LONG).show();
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
                String data =
                        URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                        URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                URLEncoder.encode("Number", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

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
        asyncObject.execute(Category,Difficulty,Number);
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