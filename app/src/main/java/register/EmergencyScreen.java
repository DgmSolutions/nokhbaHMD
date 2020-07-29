package register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.nokhbahmd.R;

public class EmergencyScreen extends AppCompatActivity {
    final String permissionToCall = Manifest.permission.CALL_PHONE;
    private static Intent phoneCallIntent;
    private Button call_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_screen);
    call_now = findViewById(R.id.call_now_id);


        call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneCallIntent = new Intent(Intent.ACTION_CALL);
                phoneCallIntent.setData(Uri.parse("tel:"+getString(R.string.phone_covid_number))); //Uri.parse("tel:your number")
                if (ActivityCompat.checkSelfPermission(EmergencyScreen.this, permissionToCall) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmergencyScreen.this, new String[]{permissionToCall}, 1);
                    return;
                }
                startActivity(phoneCallIntent);

            }
        });
        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}