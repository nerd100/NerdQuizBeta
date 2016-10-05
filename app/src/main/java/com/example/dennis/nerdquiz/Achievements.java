package com.example.dennis.nerdquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Lui on 28.09.2016.
 */
public class Achievements extends Activity{
    SharedPreferences prefs;
    ImageView A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16,A17,A18,A19,A20,A21,A22,A23,A24;
    ArrayList<ImageView> Ax=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        prefs = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);
        A1 =(ImageView)findViewById(R.id.A1);
        A2 =(ImageView)findViewById(R.id.A2);
        A3 =(ImageView)findViewById(R.id.A3);
        A4 =(ImageView)findViewById(R.id.A4);
        A5 =(ImageView)findViewById(R.id.A5);
        A6 =(ImageView)findViewById(R.id.A6);
        A7 =(ImageView)findViewById(R.id.A7);
        A8 =(ImageView)findViewById(R.id.A8);
        A9 =(ImageView)findViewById(R.id.A9);
        A10 =(ImageView)findViewById(R.id.A10);
        A11 =(ImageView)findViewById(R.id.A11);
        A12 =(ImageView)findViewById(R.id.A12);
        A13 =(ImageView)findViewById(R.id.A13);
        A14 =(ImageView)findViewById(R.id.A14);
        A15 =(ImageView)findViewById(R.id.A15);
        A16 =(ImageView)findViewById(R.id.A16);
        A17 =(ImageView)findViewById(R.id.A17);
        A18 =(ImageView)findViewById(R.id.A18);
        A19 =(ImageView)findViewById(R.id.A19);
        A20 =(ImageView)findViewById(R.id.A20);
        A21 =(ImageView)findViewById(R.id.A21);
        A22 =(ImageView)findViewById(R.id.A22);
        A23 =(ImageView)findViewById(R.id.A23);
        A24 =(ImageView)findViewById(R.id.A24);
        Ax.add(A1);
        Ax.add(A2);
        Ax.add(A3);
        Ax.add(A4);
        Ax.add(A5);
        Ax.add(A6);
        Ax.add(A7);
        Ax.add(A8);
        Ax.add(A9);
        Ax.add(A10);
        Ax.add(A11);
        Ax.add(A12);
        Ax.add(A13);
        Ax.add(A14);
        Ax.add(A15);
        Ax.add(A16);
        Ax.add(A17);
        Ax.add(A18);
        Ax.add(A19);
        Ax.add(A20);
        Ax.add(A21);
        Ax.add(A22);
        Ax.add(A23);
        Ax.add(A24);

        int counterPos=0;
        for (int i = 0; i<4;i++) {
            for (int j = 0; j < 6; j++) {

                switch(counterPos){
                    case 0:
                        if(prefs.getBoolean("A1", false)) {
                        Ax.get(counterPos).setImageResource(R.drawable.achievement_5q);
                        }
                        break;
                    case 1:
                        if(prefs.getBoolean("A2", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_10q);
                        }
                        break;
                    case 2:
                        if(prefs.getBoolean("A3", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_25q);
                        }
                        break;
                    case 3:
                        if(prefs.getBoolean("A4", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_100q);
                        }
                        break;
                    case 4:
                        if(prefs.getBoolean("A5", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_10done);
                        }
                        break;
                    case 5:
                        if(prefs.getBoolean("A6", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_25done);
                        }
                        break;
                    case 6:
                        if(prefs.getBoolean("A7", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_50done);
                        }
                        break;
                    case 7:
                        if(prefs.getBoolean("A8", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_100done);
                        }
                        break;
                    case 8:
                        if(prefs.getBoolean("A9", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_bronzecleared);
                        }
                        break;
                    case 9:
                        if(prefs.getBoolean("A10", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_silbercleared);
                        }
                        break;
                    case 10:
                        if(prefs.getBoolean("A11", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_goldcleared);
                        }
                        break;
                    case 11:
                        if(prefs.getBoolean("A12", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_diamantcleared);
                        }
                        break;
                    case 12:
                        if(prefs.getBoolean("A13", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankan);
                        }
                        break;
                    case 13:
                        if(prefs.getBoolean("A14", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankcn);
                        }
                        break;
                    case 14:
                        if(prefs.getBoolean("A15", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankkf);
                        }
                        break;
                    case 15:
                        if(prefs.getBoolean("A16", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankcu);
                        }
                        break;
                    case 16:
                        if(prefs.getBoolean("A17", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankp);
                        }
                        break;
                    case 17:
                        if(prefs.getBoolean("A18", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankalls);
                        }
                        break;
                    case 18:
                        if(prefs.getBoolean("A19", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_ranksen);
                        }
                        break;
                    case 19:
                        if(prefs.getBoolean("A20", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankar);
                        }
                        break;
                    case 20:
                        if(prefs.getBoolean("A21", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankhcg);
                        }
                        break;
                    case 21:
                        if(prefs.getBoolean("A22", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_rankng);
                        }
                        break;
                    case 22:
                        if(prefs.getBoolean("A23", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_ranknqd);
                        }
                        break;
                    case 23:
                        if(prefs.getBoolean("A24", false)) {
                            Ax.get(counterPos).setImageResource(R.drawable.achievement_100per);
                        }
                        break;

                    default:
                        break;
                }

                counterPos++;
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(Achievements.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Achievements.this, MainActivity.class));
        finish();
    }
}
