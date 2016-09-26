package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    Spinner editSpinner1,editSpinner2;
    String Question,Category,Difficulty,RA,FA1,FA2,FA3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquestion);



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

    }

    public void AddData(View view) {
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
    }

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
}
