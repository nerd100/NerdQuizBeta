package com.example.dennis.nerdquiz;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by SirLui on 09.09.2016.
 */
public class StatisticGlobal extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    ArrayList<ImageView> medals=new ArrayList<>();
    TextView niq,rang;
    ImageView k1e,k2e,k3e,k4e,k5e,
              k1m,k2m,k3m,k4m,k5m,
              k1h,k2h,k3h,k4h,k5h;

    String title = "";
    Button radarChart;

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

        radarChart=(Button) findViewById(R.id.radarChart);

        k1e = (ImageView) findViewById(R.id.k1e);
        k2e = (ImageView) findViewById(R.id.k2e);
        k3e = (ImageView) findViewById(R.id.k3e);
        k4e = (ImageView) findViewById(R.id.k4e);
        k5e = (ImageView) findViewById(R.id.k5e);
        k1m = (ImageView) findViewById(R.id.k1m);
        k2m = (ImageView) findViewById(R.id.k2m);
        k3m = (ImageView) findViewById(R.id.k3m);
        k4m = (ImageView) findViewById(R.id.k4m);
        k5m = (ImageView) findViewById(R.id.k5m);
        k1h = (ImageView) findViewById(R.id.k1h);
        k2h = (ImageView) findViewById(R.id.k2h);
        k3h = (ImageView) findViewById(R.id.k3h);
        k4h = (ImageView) findViewById(R.id.k4h);
        k5h = (ImageView) findViewById(R.id.k5h);

        medals.add(k1e);
        medals.add(k2e);
        medals.add(k3e);
        medals.add(k4e);
        medals.add(k5e);
        medals.add(k1m);
        medals.add(k2m);
        medals.add(k3m);
        medals.add(k4m);
        medals.add(k5m);
        medals.add(k1h);
        medals.add(k2h);
        medals.add(k3h);
        medals.add(k4h);
        medals.add(k5h);


        for (int i = 0; i<medals.size();i++){
            int j = shared_preferences.getInt("k"+String.valueOf(i+1)+"m",0);
            switch (j){
                case 1:
                    medals.get(i).setImageResource(R.drawable.bronzem);
                    break;
                case 2:
                    medals.get(i).setImageResource(R.drawable.silberm);
                    break;
                case 3:
                    medals.get(i).setImageResource(R.drawable.goldm);
                    break;
                case 4:
                    medals.get(i).setImageResource(R.drawable.diamandm);
                    break;
                default:
                    medals.get(i).setImageResource(R.drawable.defaultm);
                    break;
            }
        }

        radarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number", "1");
                shared_preferences_editor.apply();

                startActivity(new Intent(StatisticGlobal.this, RadChart.class));
            }
        });
    }
}
