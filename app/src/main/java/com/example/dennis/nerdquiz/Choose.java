package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import com.example.dennis.nerdquiz.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Choose extends Activity {
    public static final String GAME_PREFERENCES = "GamePrefs";
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button startbtn2;
    Spinner editSpinner1, editSpinner2; //Spinner in Choose



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecategory);

        editSpinner1 = (Spinner) findViewById(R.id.spinner1);
        editSpinner2 = (Spinner) findViewById(R.id.spinner2);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);


        startbtn2 = (Button) findViewById(R.id.start_btn2);

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Category",editSpinner1.getSelectedItem().toString());
                shared_preferences_editor.putString("Difficulty",editSpinner2.getSelectedItem().toString());
                shared_preferences_editor.commit();
                startActivity(new Intent(Choose.this, Start.class));
                finish();
            }
        });


    }
    public void onBackPressed() {

        startActivity(new Intent(Choose.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
