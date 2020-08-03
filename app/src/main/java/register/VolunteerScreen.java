package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nokhba.nokhbahmd.Model.Data;
import com.nokhba.nokhbahmd.Model.Notification;
import com.nokhba.nokhbahmd.Notifications.Api;
import com.nokhba.nokhbahmd.Notifications.Service;
import com.nokhba.nokhbahmd.Notifications.respance;
import com.nokhba.nokhbahmd.R;
import com.nokhba.nokhbahmd.classes.Datetime;
import com.nokhba.nokhbahmd.classes.SnackBar;
import com.nokhba.nokhbahmd.Model.Valunteer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nokhba.nokhbahmd.classes.CheckConx.isConnected;
import static com.nokhba.nokhbahmd.classes.DialogAlert.ShowEndDialog;

public class VolunteerScreen extends AppCompatActivity {
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText vfname,vlname,vphone_number;
    private LinearLayout linear;
    private Button send;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "VolunteerScreen";
    private ProgressDialog progressDialog;
    private static String  pattrenString = "^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$",phonePattren="\\d{10}",arabicPatren="^[\\u0621-\\u064A\\u0660-\\u0669 ]+$";;
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
                  if(isConnected(VolunteerScreen.this) == true) {
                  if(!nom.isEmpty() && !prenom.isEmpty() && !phone.isEmpty()){
                      if(nom.matches(pattrenString)  || prenom.matches(arabicPatren)){
                          if(prenom.matches(pattrenString)  || prenom.matches(arabicPatren)){
                              if(phone.matches(phonePattren)){
                                  if(!drop.isEmpty()){
                                      progressDialog = new ProgressDialog(VolunteerScreen.this);
                                      progressDialog.setCancelable(false);
                                      progressDialog.setMessage("يرجى الانتظار ...");
                                      progressDialog.show();
                                      FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                          @Override
                                          public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                              if(task.isSuccessful()){
                                                  String to = task.getResult().getToken();

                                                  Valunteer valunteer = new Valunteer(nom, prenom, phone, drop, Datetime.getDateTime(),to);
                                                  SaveData(valunteer);

                                              }else{
                                                  Toast.makeText(VolunteerScreen.this,"token not finding",Toast.LENGTH_LONG).show();
                                              }
                                          }
                                      });



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
                  }else {
                      new SnackBar().SnackBarMessage(linear,getString(R.string.checkConx), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
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
                        progressDialog.dismiss();
                        ShowEndDialog(VolunteerScreen.this);
                        pushNotification(valunteer.getService());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new SnackBar().SnackBarMessage(linear,getString(R.string.errorMssg), Snackbar.LENGTH_SHORT,getResources().getColor(R.color.Eblack));
                Log.d(TAG,e.getMessage());
            }
        });

    }
    private void pushNotification(String Body) {
        db.collection("Users")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot Snapshots) {


                for (QueryDocumentSnapshot s : Snapshots) {
                   // Users users = (Users) s.toObject(Users.class);
                      String token = s.getString("Token");
                    Data data = new Data("تقديم المساعدة", Body);
                    Notification notification = new Notification(token, data);
                    Service service = Api.getBuild().create(Service.class);
                    service.sendNotifcation(notification).enqueue(new Callback<respance>() {
                        @Override
                        public void onResponse(Call<respance> call, Response<respance> response) {
                            if (response.code() == 200) {
                                if (response.body().success != 1) {

                                } else {
                                    //else show dialog
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<respance> call, Throwable t) {
                            Toast.makeText(VolunteerScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VolunteerScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }//end push

}