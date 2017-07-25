package com.example.dennis.nerdquiz;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.UUID;

public class MainActivity extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button addbtn, startbtn1, startbtn2, gstatbtn,userNamebtn,achieve;


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
        achieve = (Button) findViewById(R.id.achieve);
        if(shared_preferences.getString("username","Default").equals("Default")){
            userNamebtn = (Button) findViewById(R.id.username);
            shared_preferences_editor.putString("username","Default").apply();
        }else{
            userNamebtn = (Button) findViewById(R.id.username);
            userNamebtn.setText(shared_preferences.getString("username","Default"));
            userNamebtn.setEnabled(false);
        }

        shared_preferences_editor.putString("DeviceId", "Default").apply();

   /*     if(!shared_preferences.getBoolean("userset",false)) {
            Toast.makeText(getApplicationContext(),shared_preferences.getString("username", "Default"),Toast.LENGTH_LONG).show();
            userNamebtn.setText(shared_preferences.getString("username", "Default"));
        }else{
            userNamebtn.setEnabled(false);
        }
*/



        startbtn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startbtn1.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    shared_preferences_editor.putString("Number", "1");
                    shared_preferences_editor.apply();
                    startActivity(new Intent(MainActivity.this, CountDownCounter.class));
                    finish();
                }
                return true;
            }
        });
        startbtn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startbtn2.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    shared_preferences_editor.putString("Number", "0");
                    shared_preferences_editor.apply();
                    startActivity(new Intent(MainActivity.this, Choose.class));
                    finish();
                }
                return true;
            }
        });

        gstatbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gstatbtn.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(new Intent(MainActivity.this, StatisticGlobal.class));
                    finish();
                }
                return true;
            }
        });
        achieve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    achieve.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(new Intent(MainActivity.this, Achievements.class));
                    finish();
                }
                return true;
            }
        });
        addbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addbtn.setBackgroundResource(R.drawable.pressedbutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(new Intent(MainActivity.this, AddQuestion.class));
                    finish();
                }
                return true;
            }
        });

        //Toast.makeText(getApplicationContext(),String.valueOf(isOnline()),Toast.LENGTH_LONG).show();
        if (isOnline() == false){               //Hier Weitermachen
            startbtn1.setEnabled(false);
            startbtn2.setEnabled(false);
            addbtn.setEnabled(false);
            startbtn1.setBackgroundResource(R.drawable.buttonbg_grey);
            startbtn2.setBackgroundResource(R.drawable.buttonbg_grey);
            addbtn.setBackgroundResource(R.drawable.buttonbg_grey);
            //startbtn1.setClickable(false);
            //startbtn2.setClickable(false);
            //startbtn1.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        }
        /*


        startbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbtn1.setBackgroundResource(R.drawable.pressedbutton);
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
                startbtn2.setBackgroundResource(R.drawable.pressedbutton);
                shared_preferences_editor.putString("Number", "0");
                shared_preferences_editor.apply();
                startActivity(new Intent(MainActivity.this, Choose.class));
                finish();
            }
        });

        gstatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gstatbtn.setBackgroundResource(R.drawable.pressedbutton);
                startActivity(new Intent(MainActivity.this, StatisticGlobal.class));
                finish();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
    */

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
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}

