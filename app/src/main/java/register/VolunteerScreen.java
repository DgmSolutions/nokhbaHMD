package register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.nokhbahmd.R;
import com.google.android.material.textfield.TextInputLayout;

public class VolunteerScreen extends AppCompatActivity {
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private  TextInputLayout vfname,vlname,vphone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_screen);
        //input text
        vfname = findViewById(R.id.vfname);
        vlname = findViewById(R.id.vlname);
        vphone_number = findViewById(R.id.vphone_number);



        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
    }
}