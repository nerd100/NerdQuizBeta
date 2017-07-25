package com.example.dennis.nerdquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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

/**
 * Created by Lui on 25.09.2016.
 */
public class BackgroundTaskScore extends AsyncTask<String,Void,String> {
    Context ctx;
    String json_url;
    String JSON_STRING;
    public String json_string;
    BackgroundTaskScore(Context ctx){
        this.ctx = ctx;
    }

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        json_url = "http://nerdquiz.000webhostapp.com/json_get_data.php";
    }

    protected String doInBackground(String... params){
        if(params[0] == "InsertScore") {
            String insert_url = "http://nerdquiz.000webhostapp.com/insertscore.php";
            String method = params[0];
            if (method.equals("InsertScore")) {
                String HashedDeviceID = params[1];
                String NickName = params[2];
                String GlobalScore = params[3];

                try {
                    URL url = new URL(insert_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("HashedDeviceID", "UTF-8") + "=" + URLEncoder.encode(HashedDeviceID, "UTF-8") + "&" +
                            URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(NickName, "UTF-8") + "&" +
                            URLEncoder.encode("Score", "UTF-8") + "=" + URLEncoder.encode(GlobalScore, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    return "Data Inserted";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return "Failed";
        }

        return "Done";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
