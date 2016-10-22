package com.example.dennis.nerdquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RadChart extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    TextView n1,n2,n3,n4,n5,n6,n7,n8,n9,n10;
    TextView p1,p2,p3,p4,p5,p6,p7,p8,p9,p10;
    private static final String JSON_ARRAY = "server_response";
    private JSONArray nameaScore = null;
    ArrayList<TextView> ns = new ArrayList<>();
    ArrayList<TextView> ps = new ArrayList<>();
    PercentFormatter percentageSymbol= new PercentFormatter();
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radarchart);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(){
                displayInterstitial();
            }
        });

        requestNewInterstitial();


        n1=(TextView)findViewById(R.id.n1);
        n2=(TextView)findViewById(R.id.n2);
        n3=(TextView)findViewById(R.id.n3);
        n4=(TextView)findViewById(R.id.n4);
        n5=(TextView)findViewById(R.id.n5);
        n6=(TextView)findViewById(R.id.n6);
        n7=(TextView)findViewById(R.id.n7);
        n8=(TextView)findViewById(R.id.n8);
        n9=(TextView)findViewById(R.id.n9);
        n10=(TextView)findViewById(R.id.n10);
        ns.add(n1);
        ns.add(n2);
        ns.add(n3);
        ns.add(n4);
        ns.add(n5);
        ns.add(n6);
        ns.add(n7);
        ns.add(n8);
        ns.add(n9);
        ns.add(n10);

        p1=(TextView)findViewById(R.id.p1);
        p2=(TextView)findViewById(R.id.p2);
        p3=(TextView)findViewById(R.id.p3);
        p4=(TextView)findViewById(R.id.p4);
        p5=(TextView)findViewById(R.id.p5);
        p6=(TextView)findViewById(R.id.p6);
        p7=(TextView)findViewById(R.id.p7);
        p8=(TextView)findViewById(R.id.p8);
        p9=(TextView)findViewById(R.id.p9);
        p10=(TextView)findViewById(R.id.p10);
        ps.add(p1);
        ps.add(p2);
        ps.add(p3);
        ps.add(p4);
        ps.add(p5);
        ps.add(p6);
        ps.add(p7);
        ps.add(p8);
        ps.add(p9);
        ps.add(p10);


        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
      /*  float katIQ1 = shared_preferences.getFloat("1",0);      //shared_praferences
        float katIQ2 = shared_preferences.getFloat("2",0);
        float katIQ3 = shared_preferences.getFloat("3",0);
        float katIQ4 = shared_preferences.getFloat("4",0);
        float katIQ5 = shared_preferences.getFloat("5",0);*/

        float aR=shared_preferences.getFloat("AnimeR",0);
        float aW=shared_preferences.getFloat("AnimeW",0);
        float ARWproc=Math.round(aR/(aR+aW)*1000)/10f;
        float sR=shared_preferences.getFloat("SerienR",0);
        float sW=shared_preferences.getFloat("SerienW",0);
        float SRWproc=Math.round(sR/(sR+sW)*1000)/10f;
        float mR=shared_preferences.getFloat("MoviesR",0);
        float mW=shared_preferences.getFloat("MoviesW",0);
        float MRWproc=Math.round(mR/(mR+mW)*1000)/10f;
        Toast.makeText(getApplicationContext(), String.valueOf(MRWproc), Toast.LENGTH_SHORT).show();
        float gR=shared_preferences.getFloat("GamesR",0);
        float gW=shared_preferences.getFloat("GamesW",0);
        float GRWproc=Math.round(gR/(gR+gW)*1000)/10f;
        float mintR=shared_preferences.getFloat("MINTR",0);
        float mintW=shared_preferences.getFloat("MINTW",0);
        float MINTRWproc=Math.round(mintR/(mintR+mintW)*1000)/10f;

        Log.d("test",String.valueOf(aR+aW)+"anim"+String.valueOf(sR+sW)+"se"+String.valueOf(mR+mW)+"mo"+String.valueOf(gR+gW)+"ga"+String.valueOf(mintR+mintW)+"mint");
        RadarChart chart = (RadarChart) findViewById(R.id.chart);

        YAxis yaxis=chart.getYAxis();
        yaxis.setAxisMinValue(0);
        yaxis.setAxisMaxValue(100);


        ArrayList<Entry> entries = new ArrayList<>();

        /*entries.add(new Entry(katIQ1, 0));
        entries.add(new Entry(katIQ2, 1));
        entries.add(new Entry(katIQ3, 2));
        entries.add(new Entry(katIQ4, 3));
        entries.add(new Entry(katIQ5, 4));*/

        entries.add(new Entry(ARWproc, 0));
        entries.add(new Entry(SRWproc, 1));
        entries.add(new Entry(MRWproc, 2));
        entries.add(new Entry(GRWproc, 3));
        entries.add(new Entry(MINTRWproc, 4));


        RadarDataSet dataset_comp1 = new RadarDataSet(entries,"");

        dataset_comp1.setColor(Color.argb(0,202,68,33));
        dataset_comp1.setDrawFilled(true);

        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();

        dataSets.add(dataset_comp1);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Anime");
        labels.add("Serien");
        labels.add("Movies");
        labels.add("Games");
        labels.add("MINT");

        RadarData data = new RadarData(labels, dataSets);
        data.setValueFormatter(percentageSymbol);
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


        BackgroundTaskGetHighscores test=new BackgroundTaskGetHighscores(this) {
            @Override
            public void onResponseReceived(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    nameaScore = jsonObject.getJSONArray(JSON_ARRAY);
                    for (int i = 0; i <ns.size() ; i++) {
                        if(i<nameaScore.length()) {
                            JSONObject jsonobject = nameaScore.getJSONObject(i);
                            String Name = jsonobject.getString("Name");
                            String Score = jsonobject.getString("max");
                            ns.get(i).setText(String.valueOf(i+1)+". "+Name);
                            ps.get(i).setText(Score);
                        }else{
                            ns.get(i).setText(String.valueOf(i+1)+".");
                            ps.get(i).setText("");
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        test.execute();

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    public void displayInterstitial(){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }

    public void onBackPressed() {

        startActivity(new Intent(RadChart.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(RadChart.this, StatisticGlobal.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
