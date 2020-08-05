package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nokhba.nokhbahmd.Model.Data;
import com.nokhba.nokhbahmd.Model.Notification;
import com.nokhba.nokhbahmd.Notifications.Api;
import com.nokhba.nokhbahmd.Notifications.Service;

import com.nokhba.nokhbahmd.Notifications.response;
import com.nokhba.nokhbahmd.R;
import com.nokhba.nokhbahmd.classes.Datetime;
import com.nokhba.nokhbahmd.Model.Help;
import com.nokhba.nokhbahmd.classes.SnackBar;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nokhba.nokhbahmd.classes.CheckConx.isConnected;
import static com.nokhba.nokhbahmd.classes.DialogAlert.ShowEndDialog;

public class HelpScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , EasyPermissions.PermissionCallbacks{

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText fname,lname,phone_number,covid_number,desc_help;
    private RadioGroup covid_you,covid_family;
    private RadioButton choix,fchoix;
    private Button btn;
    private LinearLayout linear;
    private final int locationRequestCode = 123;
    private static String  pattrenString = "^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$",phonePattren="\\d{10}",arabicPatren="^[\\u0621-\\u064A\\u0660-\\u0669 ]+$";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "HelpScreen";
    private ProgressDialog progressDialog;
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
        desc_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (desc_help.hasFocus()) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        //radio button groupe
        covid_you = (RadioGroup) findViewById(R.id.covid_you);
       covid_family = (RadioGroup) findViewById(R.id.covid_family);
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
        covid_number.setEnabled(false);
        covid_family.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.oui){
                           covid_number.setEnabled(true);
                        }else if(checkedId == R.id.non){
                            covid_number.setEnabled(false);
                        }
                    }
                }
        );

        btn = findViewById(R.id.send);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String nom = fname.getText().toString().trim();
                 String prenom =lname.getText().toString().trim();
                 String phone = phone_number.getText().toString().trim();
                 String cnum=covid_number.getText().toString().trim();
                 String desc=desc_help.getText().toString();

                int checked =covid_you.getCheckedRadioButtonId();
                choix = (RadioButton) findViewById(checked);
                int check =covid_family.getCheckedRadioButtonId();
                fchoix = (RadioButton) findViewById(check);
                String drop =autoCompleteTextView.getText().toString();
                String Ycovide =choix.getText().toString();
                String Fcovid =fchoix.getText().toString();
                if(hasLocation()) {
                      if(CheckGps()) {
                          if(isConnected(HelpScreen.this) == true ) {
                if(!nom.isEmpty() && !prenom.isEmpty() && !phone.isEmpty() && !desc.isEmpty()){
                     if(!drop.isEmpty()){
                         if(nom.matches(pattrenString) || nom.matches(arabicPatren)){
                             if(prenom.matches(pattrenString) || prenom.matches(arabicPatren)){
                                 if(phone.matches(phonePattren)){
                                     progressDialog = new ProgressDialog(HelpScreen.this);
                                     progressDialog.setCancelable(false);
                                     progressDialog.setMessage("يرجى الانتظار ...");
                                     progressDialog.show();
                                     //get localisation and insert data
                                     switch (fchoix.getId()){

                                         case R.id.non:
                                             FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                     if(task.isSuccessful()){
                                                         String to = task.getResult().getToken();
                                                         final String des=desc.replaceAll("( )+", " ");
                                                         saveData(phone);
                                                         getCurrentLocation(nom,prenom,phone,Ycovide,Fcovid,drop,des,"0",to);

                                                     }else{
                                                         Toast.makeText(HelpScreen.this,"token not finding",Toast.LENGTH_LONG).show();
                                                     }
                                                 }
                                             });

                                             break;
                                         case R.id.oui:
                                             FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                     if(task.isSuccessful()){
                                                         String to = task.getResult().getToken();
                                                         final String des=desc.replaceAll("( )+", " ");
                                                         saveData(phone);
                                                         getCurrentLocation(nom,prenom,phone,Ycovide,Fcovid,drop,des,cnum,to);

                                                     }else{
                                                         Toast.makeText(HelpScreen.this,"token not finding",Toast.LENGTH_LONG).show();
                                                     }
                                                 }
                                             });

                                             break;
                                     }

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
                }else{
                    new SnackBar().SnackBarMessage(linear,getString(R.string.checkConx), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                }


                      }else{
                          EnableGps();
                      }
                }else{
                    PermissionLocation();
                }

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
    private void getCurrentLocation(String n,String p,String h,String yc,String fc,String drop,String d ,String cnum,String to){
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
                                Map<String, Double> localisation = new HashMap<>();
                                localisation.put("latitude",latitude);
                                localisation.put("longtitude",longtitude);
                                Help help =new Help(n,p,h,yc,fc,drop,d,Integer.parseInt(cnum),Datetime.getDateTime(),localisation,to);
                                SaveData(help);


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
    }//end enbleGPS
    private void SaveData(Help help) {
        db.collection("Help").document(help.getPhone()).set(help)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        ShowEndDialog(HelpScreen.this);
                        pushNotification(help.getService());

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                new SnackBar().SnackBarMessage(linear, getString(R.string.errorMssg), Snackbar.LENGTH_SHORT, getResources().getColor(R.color.Eblack));
                Log.d(TAG, e.getMessage());
            }
        });

    }//end seve data

    private void pushNotification(String Body){
       db.collection("Users")
               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot Snapshots) {


               for(QueryDocumentSnapshot s:Snapshots){
                  // Users users= (Users) s.toObject(Users.class);
                   String token = s.getString("Token");
                   Data data=new Data("طلب المساعدة",Body);
                   Notification notification =new Notification(token,data);
                   Service service = Api.getBuild().create(Service.class);
                   service.sendNotifcation(notification).enqueue(new Callback<response>() {
                       @Override
                       public void onResponse(Call<response> call, Response<response> response) {
                           if (response.code() == 200) {
                               if (response.body().success != 1) {

                               } // else show dialog
                           }
                       }
                       @Override
                       public void onFailure(Call<response> call, Throwable t) {
                           Toast.makeText(HelpScreen.this,t.getMessage(),Toast.LENGTH_LONG).show();

                       }
                   });
               }


           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(HelpScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
           }
       });


    }//end push notification
    //***********************************permission*******************************
    private Boolean hasLocation() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    public void PermissionLocation() {
        // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.mssg_localisation),
                    locationRequestCode,
                    Manifest.permission.ACCESS_FINE_LOCATION);


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
    public Boolean CheckGps(){
        LocationManager locationManager ;
        boolean GpsStatus ;

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GpsStatus;
    }
    public void saveData(String p) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone",p );
        editor.apply();
        Toast.makeText(this, "مرحبا بك", Toast.LENGTH_SHORT).show();

    }
}