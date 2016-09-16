package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

/**
 * Created by Lui on 02.09.2016.
 */
public class SplashScreen extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animsplash);

        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/raw/splashvideo");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        });
        videoView.start();

    }
}
