package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.motasim.photo.R;

public class SplashActivity extends AppCompatActivity {

    TextView mTextView;
    Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(6000);
                    Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        mTextView = findViewById(R.id.SplashText);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_text_animation);

        mTextView.setAnimation(mAnimation);
    }
}

