package com.example.nokhbahmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private static int SPLASH_TIME=4000;
    private Animation topAnim,bottomAnim,middleAnim;
    private LottieAnimationView first,second,third,forth;
    private SharedPreferences BoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.textView);
        imageView= findViewById(R.id.n_logo);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        middleAnim = AnimationUtils.loadAnimation(this,R.anim.middle_anim);
        first = findViewById(R.id.animation_view);
        second = findViewById(R.id.animation_view_slider);
        third = findViewById(R.id.animation_view2);
        forth = findViewById(R.id.animation_view3);
        textView.setAnimation(middleAnim);
        imageView.setAnimation(middleAnim);
        first.setAnimation(topAnim);
        second.setAnimation(topAnim);
        third.setAnimation(bottomAnim);
        forth.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // BoardingScreen = getSharedPreferences("BoardingScreen",MODE_PRIVATE);
                // boolean FirstTime = BoardingScreen.getBoolean("firstTime",true);
                // if(FirstTime)
                //{
                //    SharedPreferences.Editor editor =BoardingScreen.edit();
                //    editor.putBoolean("firstTime",false);
                //    editor.commit();

                    Intent intent = new Intent(SplashScreen.this, BoardingScreen.class);
                    startActivity(intent);
                    finish();
                  //  }
              //  else
             //   {
              //      Intent intent = new Intent(SplashScreen.this, MainActivity.class);
               //     startActivity(intent);
               //     finish();
               // }
            }
        },SPLASH_TIME);
    }
}