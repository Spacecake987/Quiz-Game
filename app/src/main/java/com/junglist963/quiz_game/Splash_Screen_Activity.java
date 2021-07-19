package com.junglist963.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen_Activity extends AppCompatActivity {

    ImageView imageViewSplash;
    TextView textViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageViewSplash = findViewById(R.id.imageViewSplash);
        textViewSplash = findViewById(R.id.textViewSplash);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_text_anim);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_img_anim);

        textViewSplash.startAnimation(animation);
        imageViewSplash.startAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Screen_Activity.this, Login_Activity.class);
                startActivity(i);
                finish();

            }
        }, 5000);
    }
}