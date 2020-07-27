package com.example.nokhbahmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import register.AboutUsScreen;
import register.EmergencyScreen;
import register.HelpScreen;
import register.RulesScreen;
import register.VolunteerScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.help_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HelpScreen.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.volunter_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VolunteerScreen.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.rules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RulesScreen.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.emergency_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmergencyScreen.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.about_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutUsScreen.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        MaterialDialog mDialog = new MaterialDialog.Builder(MainActivity.this).setAnimation(R.raw.exit)
                .setTitle("خروج")
                .setMessage("هل تريد مغادرة التطبيق ؟")
                .setCancelable(true)
                .setPositiveButton("نعم", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();
                    }
                })
                .setNegativeButton("لا",new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        LottieAnimationView animationView = mDialog.getAnimationView();
        animationView.setPadding(200,200,200,200);
        animationView.loop(false);
        // Show Dialog
        mDialog.show();
    }
}