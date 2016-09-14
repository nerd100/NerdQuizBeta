package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.icu.util.RangeValueIterator;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by SirLui on 09.09.2016.
 */
public class StatisticGlobal extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    ArrayList<ImageView> test=new ArrayList<>();
    TextView niq,rang;
    ImageView k1m,k2m,k3m,k4m,k5m;
//k6m
    String title = "";

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statisticglobal);
        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);
        title = shared_preferences.getString("Rang","Default");
        niq = (TextView) findViewById(R.id.niq);
        rang = (TextView) findViewById(R.id.title);
        niq.setText(String.valueOf(shared_preferences.getInt("countNerdIQ",0)));
        rang.setText(title);
        //LineChart chart = (LineChart) findViewById(R.id.chart);
        k1m = (ImageView) findViewById(R.id.k1m);
        k2m = (ImageView) findViewById(R.id.k2m);
        k3m = (ImageView) findViewById(R.id.k3m);
        k4m = (ImageView) findViewById(R.id.k4m);
        k5m = (ImageView) findViewById(R.id.k5m);
     //   k6m = (ImageView) findViewById(R.id.k6m);

        test.add(k1m);
        test.add(k2m);
        test.add(k3m);
        test.add(k4m);
        test.add(k5m);
    //    test.add(k6m);

        for (int i = 0; i<test.size();i++){
            int j = shared_preferences.getInt("k"+String.valueOf(i+1)+"m",0);
            switch (j){
                case 1:
                    test.get(i).setImageResource(R.drawable.bronzem);
                    break;
                case 2:
                    test.get(i).setImageResource(R.drawable.silberm);
                    break;
                case 3:
                    test.get(i).setImageResource(R.drawable.goldm);
                    break;
                case 4:
                    test.get(i).setImageResource(R.drawable.diamandm);
                    break;
                default:
                    test.get(i).setImageResource(R.drawable.defaultm);
                    break;
            }
        }


    }
}
