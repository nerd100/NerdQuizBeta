package com.example.dennis.nerdquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.dennis.nerdquiz.R;

/**
 * Created by Dennis on 25.08.2016.
 */
public class AddQuestion extends Activity {

    EditText editQuestion, editRA, editFA1, editFA2, editFA3;
    Button btnAddData;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    Spinner editSpinner1,editSpinner2;
    String Question,Category,Difficulty,RA,FA1,FA2,FA3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquestion);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        editSpinner1 = (Spinner) findViewById(R.id.editSpinner1);
        editSpinner2 = (Spinner) findViewById(R.id.editSpinner2);
        editQuestion = (EditText) findViewById(R.id.editQuestion);
        editRA = (EditText) findViewById(R.id.editRA);
        editFA1 = (EditText) findViewById(R.id.editFA1);
        editFA2 = (EditText) findViewById(R.id.editFA2);
        editFA3 = (EditText) findViewById(R.id.editFA3);
        btnAddData = (Button) findViewById(R.id.add_button);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo.isConnected()){
            btnAddData.setEnabled(true);
        }else{
            btnAddData.setEnabled(true);        //WICHTIG DAS true WIEDER zu false machen
        }

        btnAddData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnAddData.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Question = editQuestion.getText().toString();
                    Category = editSpinner1.getSelectedItem().toString();
                    Difficulty = editSpinner2.getSelectedItem().toString();
                    RA = editRA.getText().toString();
                    FA1 = editFA1.getText().toString();
                    FA2 = editFA2.getText().toString();
                    FA3 = editFA3.getText().toString();
                    String method = "Insert";
                    BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                    backgroundTask.execute(method,Category,Difficulty,Question,RA,FA1,FA2,FA3);
                    clearFields();
                    btnAddData.setBackgroundResource(R.drawable.buttonbg);

                    //Achievements
                    int tmpcaq=shared_preferences.getInt("counterAddq",0);
                    tmpcaq+=1;
                    shared_preferences_editor=shared_preferences.edit();
                    shared_preferences_editor.putInt("counterAddq",tmpcaq).apply();
                    if(tmpcaq<=100){
                        switch(tmpcaq){
                            case 10:
                                shared_preferences_editor.putBoolean("A5",true).apply();
                                break;
                            case 25:
                                shared_preferences_editor.putBoolean("A6",true).apply();
                                break;
                            case 50:
                                shared_preferences_editor.putBoolean("A7",true).apply();
                                break;
                            case 100:
                                shared_preferences_editor.putBoolean("A8",true).apply();
                                break;
                        }
                }

            }
                return true;
            }
        });
    }

  /*  public void AddData(View view) {
        Question = editQuestion.getText().toString();
        Category = editSpinner1.getSelectedItem().toString();
        Difficulty = editSpinner2.getSelectedItem().toString();
        RA = editRA.getText().toString();
        FA1 = editFA1.getText().toString();
        FA2 = editFA2.getText().toString();
        FA3 = editFA3.getText().toString();
        String method = "Insert";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,Category,Difficulty,Question,RA,FA1,FA2,FA3);
        clearFields();
    }*/

    public void onBackPressed() {

        startActivity(new Intent(AddQuestion.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public void clearFields(){
        editQuestion.setText("");
        editRA.setText("");
        editFA1.setText("");
        editFA2.setText("");
        editFA3.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AddQuestion.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
