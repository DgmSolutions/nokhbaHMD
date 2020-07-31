package com.example.nokhbahmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import register.AboutUsScreen;
import register.EmergencyScreen;
import register.HelpScreen;
import register.RulesScreen;
import register.VolunteerScreen;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , EasyPermissions.PermissionCallbacks{
    private final int locationRequestCode = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionLocation();
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
    //**************************************Localisation dialog**********************


    private Boolean hasLocation() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

   public void PermissionLocation() {
        if (hasLocation()) {

            //  getCurrentLocation();


        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.mssg_localisation),
                    locationRequestCode,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}