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

public class VolunteerScreen extends AppCompatActivity {
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText vfname,vlname,vphone_number;
    private LinearLayout linear;
    private Button send;
    private int   locationRequestCode=123;
    private static String  pattrenString = "^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$",phonePattren="\\d{10}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_screen);
        EnableGps();
        //input text
        vfname = findViewById(R.id.vfname);
       vlname = findViewById(R.id.vlname);
        vphone_number = findViewById(R.id.vphone_number);
        linear=findViewById(R.id.vlinear);
        send=findViewById(R.id.vsend);
        textInputLayout = findViewById(R.id.dropdown_menu_layout);
        autoCompleteTextView =findViewById(R.id.dropdown_menu_id);
        autoCompleteTextView.setKeyListener(null);
        String item1 = getResources().getText(R.string.volunter_simple).toString();
        String item2 = getResources().getText(R.string.volunter_dr1).toString();
        String item3 = getResources().getText(R.string.volunter_dr).toString();
        String item4 = getResources().getText(R.string.volunter_money).toString();
        String[] itmes = new String[]{item1,item2,item3,item4};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(VolunteerScreen.this,R.layout.dropdown_items,itmes);
        autoCompleteTextView.setAdapter(adapter);
          send.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String nom =vfname.getText().toString().trim();
                  String prenom=vlname.getText().toString().trim();
                  String phone=vphone_number.getText().toString().trim();
                  String drop =autoCompleteTextView.getText().toString();
                  if(!nom.isEmpty() && !prenom.isEmpty() && !phone.isEmpty()){
                      if(nom.matches(pattrenString)){
                          if(prenom.matches(pattrenString)){
                              if(phone.matches(phonePattren)){
                                  if(!drop.isEmpty()){

                                  }else {
                                      new SnackBar().SnackBarMessage(linear,getString(R.string.serviceType), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                                  }

                              }else {
                                  new SnackBar().SnackBarMessage(linear,getString(R.string.phoneformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                              }
                          }else {
                              new SnackBar().SnackBarMessage(linear,getString(R.string.prenomformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                          }
                      }else {
                          new SnackBar().SnackBarMessage(linear,getString(R.string.nomformat), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                      }
                  }else {
                      new SnackBar().SnackBarMessage(linear,getString(R.string.champ), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                  }
               getCurrentLocation();
              }
          });
        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
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
                            LocationServices.getFusedLocationProviderClient(VolunteerScreen.this)
                                    .removeLocationUpdates(this);
                            LocationManager lm =(LocationManager)VolunteerScreen.this.getSystemService(LOCATION_SERVICE);

                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int index = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longtitude = locationResult.getLocations().get(index).getLongitude();
                                Toast.makeText(VolunteerScreen.this,latitude +" "+longtitude,Toast.LENGTH_LONG).show();
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

                                r.startResolutionForResult(VolunteerScreen.this, locationRequestCode);


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