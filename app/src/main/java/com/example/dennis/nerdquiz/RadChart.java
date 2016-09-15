package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RadChart extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        float katIQ1 = shared_preferences.getFloat("1",0);      //shared_praferences
        float katIQ2 = shared_preferences.getFloat("2",0);
        float katIQ3 = shared_preferences.getFloat("3",0);
        float katIQ4 = shared_preferences.getFloat("4",0);
        float katIQ5 = shared_preferences.getFloat("5",0);

        RadarChart chart = (RadarChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(katIQ1, 0));
        entries.add(new Entry(katIQ2, 1));
        entries.add(new Entry(katIQ3, 2));
        entries.add(new Entry(katIQ4, 3));
        entries.add(new Entry(katIQ5, 4));

        RadarDataSet dataset_comp1 = new RadarDataSet(entries,"");

        dataset_comp1.setColor(Color.CYAN);
        dataset_comp1.setDrawFilled(true);

        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();

        dataSets.add(dataset_comp1);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Anime");
        labels.add("Serien");
        labels.add("Movies");
        labels.add("Games");
        labels.add("Assi");

        RadarData data = new RadarData(labels, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(10f);
        chart.setWebColor(Color.WHITE);
        chart.setWebColorInner(Color.WHITE);
        chart.setData(data);
        chart.setWebLineWidthInner(1.5f);
        chart.setTouchEnabled(false);
        chart.setDescription("");
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setTextSize(15f);
        chart.getYAxis().setEnabled(false);
        chart.invalidate();
        chart.animate();

    }
}
