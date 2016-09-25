package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.UUID;

public class MainActivity extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button addbtn, startbtn1, startbtn2, gstatbtn,userNamebtn;

    EditText usernameedit;
    //private AdView mAdView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);
        shared_preferences_editor = shared_preferences.edit();

        startbtn1 = (Button) findViewById(R.id.btn_start1);
        startbtn2 = (Button) findViewById(R.id.btn_start2);
        addbtn = (Button) findViewById(R.id.addquestion);
        gstatbtn = (Button) findViewById(R.id.gstatistic);
        userNamebtn = (Button) findViewById(R.id.username);
        usernameedit = (EditText) findViewById(R.id.usernameedit);
        shared_preferences_editor.putString("username","Default");
        shared_preferences_editor.putString("DeviceId", "Default");
        shared_preferences_editor.apply();
        userNamebtn.setText(shared_preferences.getString("username","Default"));



        startbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor.putString("Number", "1");
                shared_preferences_editor.apply();
                //startActivity(new Intent(MainActivity.this, CountDownCounter.class));
                startActivity(new Intent(MainActivity.this, CountDownCounter.class));
                finish();
            }
        });

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor.putString("Number", "0");
                shared_preferences_editor.apply();
                startActivity(new Intent(MainActivity.this, Choose.class));
                finish();
            }
        });

        gstatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatisticGlobal.class));
                finish();
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuestion.class));
                finish();
            }
        });

        userNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new NicknameDialog();
                dialog.show(getFragmentManager(), "tag");
//                userNamebtn.setText(usernameedit.getText().toString());
            }
        });

        //Get device-ID and hash it.
        if (shared_preferences.getString("DeviceId", "Default").equals("Default")) {
            final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();

            shared_preferences_editor.putString("DeviceId", deviceId);
            shared_preferences_editor.apply();
        }



      /*  Question = editQuestion.getText().toString();
        Category = editSpinner1.getSelectedItem().toString();
        Difficulty = editSpinner2.getSelectedItem().toString();
        RA = editRA.getText().toString();
        FA1 = editFA1.getText().toString();
        FA2 = editFA2.getText().toString();
        FA3 = editFA3.getText().toString();
        String method = "Insert";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,Category,Difficulty,Question,RA,FA1,FA2,FA3);*/
    }


}

