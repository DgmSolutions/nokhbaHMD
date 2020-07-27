package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nokhbahmd.MainActivity;
import com.example.nokhbahmd.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class HelpScreen extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText fname,lname,phone_number,covid_number,desc_help;
    private RadioRealButtonGroup covid_you,covid_family;
    private RadioRealButton c_yes,c_no,f_yes,f_no;
    private Button btn;
    final String permissionToFineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        // all input text
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phone_number = findViewById(R.id.phone_number);
        covid_number = findViewById(R.id.covid_num);
        desc_help = findViewById(R.id.desc_help);
        //radio button groupe
        covid_you = (RadioRealButtonGroup) findViewById(R.id.covid_you);
        covid_family = (RadioRealButtonGroup) findViewById(R.id.covid_family);
        //radio button
        c_yes = (RadioRealButton) findViewById(R.id.c_yes);
        c_no = (RadioRealButton) findViewById(R.id.c_no);
        f_yes = (RadioRealButton) findViewById(R.id.f_yes);
        f_no = (RadioRealButton) findViewById(R.id.f_no);

        //Get Localisation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //check Android Version

        // check Permission
        btn = findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if (ActivityCompat.checkSelfPermission(HelpScreen.this, permissionToFineLocation) == PackageManager.PERMISSION_GRANTED) {
                        //Get Location
                        getLocation();
                    } else {
                        requestLocationPermissions();
                    }

              /*  BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(HelpScreen.this).setAnimation(R.raw.sended)
                        .setTitle("تم الإرسال بنجاح")
                        .setMessage("ستقوم النخبة بالاتصال بك في أقرب وقت")
                        .setCancelable(true)
                        .setPositiveButton("حسنا", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                LottieAnimationView animationView = mDialog.getAnimationView();
                animationView.setPadding(100, 100, 100, 100);
                animationView.loop(false);
                // Show Dialog
                mDialog.show();*/
            }
        });


        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        textInputLayout = findViewById(R.id.dropdown_menu_layout);
        autoCompleteTextView = findViewById(R.id.dropdown_menu_id);
        autoCompleteTextView.setKeyListener(null);
        String item1 = getResources().getText(R.string.other).toString();
        String item2 = getResources().getText(R.string.dr).toString();
        String item3 = getResources().getText(R.string.dr1).toString();
        String[] itmes = new String[]{item1, item2, item3};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HelpScreen.this, R.layout.dropdown_items, itmes);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HelpScreen.this,permissionToFineLocation)) {
            MaterialDialog mDialog = new MaterialDialog.Builder(HelpScreen.this).setAnimation(R.raw.location)
                    .setTitle("استخدام GPS")
                    .setMessage("نحن بحاجة الى خدمة GPS حتى يشتغل التطبيق")
                    .setCancelable(true)
                    .setPositiveButton("حسنا", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            ActivityCompat.requestPermissions(HelpScreen.this, new String[]{permissionToFineLocation}, 2);
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            LottieAnimationView animationView = mDialog.getAnimationView();
            animationView.setPadding(100, 100, 100, 100);
            // Show Dialog
            mDialog.show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permissionToFineLocation}, 2);
        }
    }



    //get location method
    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //Initialize Geocoder
                        Geocoder geocoder = new Geocoder(HelpScreen.this, Locale.getDefault());
                        //Initialize adresse list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        // show countryName
                        Toast.makeText(HelpScreen.this, addresses.get(0).getCountryName(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}