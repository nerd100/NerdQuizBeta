package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Dennis on 22.09.2016.
 */
public class CountDownCounter extends Activity{

    TextView title;
    TextView counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        title = (TextView) findViewById(R.id.title_counter);
        counter = (TextView) findViewById(R.id.counter);

        title.setText("Ready");
        counter.setText("3");
        count_down();

    }
    public void count_down(){

        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                counter.setText("GO!");
                startActivity(new Intent(CountDownCounter.this, Start.class));
                finish();
            }
        }.start();


    }
}
