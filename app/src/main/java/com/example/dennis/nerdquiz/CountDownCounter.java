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

    CountDownTimer countdown;
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

    @Override
    public void onBackPressed() {
        countdown.cancel();
        startActivity(new Intent(CountDownCounter.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public void count_down(){

        countdown = new CountDownTimer(4000, 500) {

            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished/1000 >= 1){
                    counter.setText(String.valueOf(millisUntilFinished / 1000));
                }else {
                    counter.setText("GO");
                }
            }

            public void onFinish() {
                startActivity(new Intent(CountDownCounter.this, Start.class));
                finish();
            }
        }.start();


    }
}
