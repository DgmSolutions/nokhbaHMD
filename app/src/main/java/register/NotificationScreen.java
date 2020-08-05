package register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.nokhba.nokhbahmd.Model.Data;
import com.nokhba.nokhbahmd.Model.Notification;
import com.nokhba.nokhbahmd.R;

import Adapter.RecyclerAdapter;

public class NotificationScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference not ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);
        not =db.collection("Data");
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.RecyclerView_id);
        SharedPreferences sharedPrefs = getSharedPreferences("data", MODE_PRIVATE);

        String p= sharedPrefs.getString("phone", "");


            Query q = not.orderBy("date", Query.Direction.DESCENDING);
            Query query=not.whereEqualTo("phone",p);

            FirestoreRecyclerOptions<Data> options = new FirestoreRecyclerOptions.Builder<Data>()
                   // .setQuery(query, Data.class)
                    .setQuery(query,Data.class)
                    .build();
            adapter = new RecyclerAdapter(options);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(NotificationScreen.this));
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);

            adapter.setClick(new RecyclerAdapter.ItemClick() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    Data obj = documentSnapshot.toObject(Data.class);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NotificationScreen.this);
                    builder.setCancelable(true)
                            .setMessage(obj.getBody());

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationScreen.this);
                builder.setTitle("حذف الطلبات")
                        .setCancelable(false)
                        .setMessage("هل تريد حذف الطلب ؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.DeleteItem(viewHolder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button negative = alertDialog.getButton(alertDialog.BUTTON_NEGATIVE);
                negative.setBackgroundColor(Color.TRANSPARENT);
                negative.setTextColor(Color.BLACK);



            }
        }).attachToRecyclerView(recyclerView);

        // Back Button
        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}