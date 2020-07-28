package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nokhbahmd.R;
import com.example.nokhbahmd.classes.SnackBar;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HelpScreen extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText fname,lname,phone_number,covid_number,desc_help;
    private RadioGroup covid_you,covid_family;
    private RadioButton choix,fchoix;
    private Button btn;
    private LinearLayout linear;
    private final int locationRequestCode = 123;
    private static String  pattrenString = "^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$",phonePattren="\\d{10}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        //enable GPS
        EnableGps();
        // all input text
        linear=findViewById(R.id.vlinear);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phone_number = findViewById(R.id.phone_number);
        covid_number = findViewById(R.id.covid_num);
        desc_help = findViewById(R.id.desc_help);
        //radio button groupe
        covid_you = (RadioGroup) findViewById(R.id.covid_you);
       covid_family = (RadioGroup) findViewById(R.id.covid_family);
        //radio button


        //dropdown
        textInputLayout = findViewById(R.id.dropdown_menu_layout);
        autoCompleteTextView = findViewById(R.id.dropdown_menu_id);
        autoCompleteTextView.setKeyListener(null);
        String item1 = getResources().getText(R.string.other).toString();
        String item2 = getResources().getText(R.string.dr).toString();
        String item3 = getResources().getText(R.string.dr1).toString();
        String[] itmes = new String[]{item1, item2, item3};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HelpScreen.this, R.layout.dropdown_items, itmes);
        autoCompleteTextView.setAdapter(adapter);

        //Toast.makeText(HelpScreen.this,,Toast.LENGTH_LONG).show();
        btn = findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String nom = fname.getText().toString().trim();
                 String prenom =lname.getText().toString().trim();
                 String phone = phone_number.getText().toString().trim();
                 String cnum=covid_number.getText().toString().trim();
                 String desc=desc_help.getText().toString().trim();
                int checked =covid_you.getCheckedRadioButtonId();
                choix = (RadioButton) findViewById(checked);
                int check =covid_family.getCheckedRadioButtonId();
                fchoix = (RadioButton) findViewById(check);
                String drop =autoCompleteTextView.getText().toString();
                if(!nom.isEmpty() && !prenom.isEmpty() && !phone.isEmpty() && !cnum.isEmpty() && !desc.isEmpty()){
                     if(!drop.isEmpty()){
                         if(nom.matches(pattrenString)){
                             if(prenom.matches(pattrenString)){
                                 if(phone.matches(phonePattren)){
                                     //insert
                                     getCurrentLocation();
                                 }else{
                                     new SnackBar().SnackBarMessage(linear,getString(R.string.phoneformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                                 }


                             }else{
                                 new SnackBar().SnackBarMessage(linear,getString(R.string.prenomformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                             }

                         }else{
                             new SnackBar().SnackBarMessage(linear,getString(R.string.nomformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                         }

                     }else{
                         new SnackBar().SnackBarMessage(linear,getString(R.string.serviceType), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                     }
                }else{
                 new SnackBar().SnackBarMessage(linear,getString(R.string.champ), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
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

    }

    //get location method
    private void getCurrentLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        } else {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,
                    new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(HelpScreen.this)
                                    .removeLocationUpdates(this);
                            LocationManager lm =(LocationManager)HelpScreen.this.getSystemService(LOCATION_SERVICE);

                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int index = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longtitude = locationResult.getLocations().get(index).getLongitude();
                               Toast.makeText(HelpScreen.this,latitude +" "+longtitude,Toast.LENGTH_LONG).show();
                            }

                        }
                    }, Looper.getMainLooper());

        }
    }
    private void EnableGps() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException r = (ResolvableApiException) e;

                                r.startResolutionForResult(HelpScreen.this, locationRequestCode);


                            } catch (IntentSender.SendIntentException s) {
                                s.printStackTrace();
                            } catch (ClassCastException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;


                    }
                }
            }
        });
    }
}