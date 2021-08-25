package com.aakash.bpibs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {
    ImageView logo, background;
    private LottieAnimationView lottieAnimationView;

    private static final int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.bpibs_logo);
        background = findViewById(R.id.back_white);
        lottieAnimationView = findViewById(R.id.lottie_layer_name);
        lottieAnimationView.playAnimation();
        lottieAnimationView.setRepeatCount(3);
//        background.animate().translationY(-1600).setDuration(1000).setStartDelay(3000);
//        lottieAnimationView.animate().translationY(500).setDuration(3500).setStartDelay(1);


        new Handler().postDelayed(() -> {
            Intent splashIntent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(splashIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, SPLASH_TIME_OUT);


    }

}