package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

/**
 * Created by Lui on 29.09.2016.
 */
public abstract class BackgroundTaskGetHighscores extends AsyncTask<String,Void,String> implements GetDataFromTask {

    Activity activity;
    Context ctx;
    String json_url;
    String JSON_STRING;
    public String json_string;
    private static final String JSON_ARRAY = "server_response";
    private JSONArray nameaScores = null;
    BackgroundTaskGetHighscores(Context ctx){
        this.ctx = ctx;
    }

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    BackgroundTaskGetHighscores(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        json_url = "http://nerdquiz.000webhostapp.com/json_get_scores.php";
    }

    protected String doInBackground(String... params){
        String JSON_STRING;
        String result = "";
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);
}
