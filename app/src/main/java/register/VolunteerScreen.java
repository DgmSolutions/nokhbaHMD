package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nokhbahmd.R;
import com.example.nokhbahmd.classes.Datetime;
import com.example.nokhbahmd.classes.Help;
import com.example.nokhbahmd.classes.SnackBar;
import com.example.nokhbahmd.classes.Valunteer;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import static com.example.nokhbahmd.classes.DialogAlert.ShowEndDialog;

public class VolunteerScreen extends AppCompatActivity {
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText vfname,vlname,vphone_number;
    private LinearLayout linear;
    private Button send;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "VolunteerScreen";
    private static String  pattrenString = "^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$",phonePattren="\\d{10}";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_screen);

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
                                      progressDialog = new ProgressDialog(VolunteerScreen.this);
                                      progressDialog.setCancelable(false);
                                      progressDialog.setMessage("يرجى الانتظار ...");
                                      progressDialog.show();
                                     Valunteer valunteer=new Valunteer(nom,prenom,phone,drop, Datetime.getDateTime());
                                     SaveData(valunteer);
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

              }
          });
        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void SaveData(Valunteer valunteer){
        db.collection("Volunteer").document(valunteer.getPhone()).set(valunteer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ShowEndDialog(VolunteerScreen.this);
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new SnackBar().SnackBarMessage(linear,getString(R.string.errorMssg), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                Log.d(TAG,e.getMessage());
            }
        });

    }

}