package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;import com.example.dennis.nerdquiz.MainActivity;import com.example.dennis.nerdquiz.R;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
/**
 * Created by Lui on 02.09.2016.
 */
public class SplashScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //ImageView screenView = (ImageView) findViewById(R.id.anim);
        //screenView.setBackgroundResource(R.drawable.splashanimation);
        //AnimationDrawable splashAnimation = (AnimationDrawable) screenView.getBackground();
        //splashAnimation.start();


        Thread timerThread = new Thread(){
            public void run(){
                try{

                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
