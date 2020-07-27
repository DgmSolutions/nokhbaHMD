package com.example.nokhbahmd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import slider.SliderAdapter;

public class BoardingScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private TextView[] dots;
    SliderAdapter sliderAdapter;
    private Button start,next_button,previous_button;
    private Animation anim;
    private int posnow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boarding_screen);

         viewPager = findViewById(R.id.slider);
         dotLayout = findViewById(R.id.dot);
         start = findViewById(R.id.start);
         next_button = findViewById(R.id.next_btn);
         previous_button = findViewById(R.id.previous_btn);

    sliderAdapter = new SliderAdapter(this);
    viewPager.setAdapter(sliderAdapter);
    addDots(0);
    viewPager.addOnPageChangeListener(changeListener);
    }
    // Start Button
    public void start(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    // Previous Button
    public void previous(View view){
        viewPager.setCurrentItem(posnow-1);
    }
    // Next Button
    public void next(View view){
        viewPager.setCurrentItem(posnow+1);
    }
    // Dots
    private void addDots(int p){

        dots = new TextView[4];
        dotLayout.removeAllViews();
        for (int i=0;i<dots.length;i++){
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(60);
            dotLayout.addView(dots[i]);
        }
        if (dots.length>0){
            dots[p].setTextColor(getResources().getColor(R.color.green));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        addDots(position);
        posnow= position;
        switch (position){
            case 0: start.setVisibility(View.INVISIBLE);
                    next_button.setVisibility(View.VISIBLE);
                    previous_button.setVisibility(View.INVISIBLE);
                    break;
            case 1: start.setVisibility(View.INVISIBLE);
                    next_button.setVisibility(View.VISIBLE);
                    previous_button.setVisibility(View.VISIBLE);
                break;
            case 2: start.setVisibility(View.INVISIBLE);
                    next_button.setVisibility(View.VISIBLE);
                    previous_button.setVisibility(View.VISIBLE);
                break;
            case 3: start.setVisibility(View.VISIBLE);
                    next_button.setVisibility(View.INVISIBLE);
                    previous_button.setVisibility(View.VISIBLE);
                    anim = AnimationUtils.loadAnimation(BoardingScreen.this,R.anim.right_anim);
                    start.setAnimation(anim);
            break;
        }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}