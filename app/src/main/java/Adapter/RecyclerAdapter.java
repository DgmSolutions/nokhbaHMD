package Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.nokhba.nokhbahmd.Model.Data;

import com.nokhba.nokhbahmd.R;

public class RecyclerAdapter extends FirestoreRecyclerAdapter<Data,RecyclerAdapter.NotificationHolder> {
    private ItemClick click;

    public RecyclerAdapter(@NonNull FirestoreRecyclerOptions<Data> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NotificationHolder holder, int position, @NonNull Data model) {
           holder.not_title.setText(model.getTitle());
           holder.not_count.setText(String.valueOf(position+1));
           holder.not_date.setText(model.getDate());

    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row, parent, false);
        return new NotificationHolder(v);
    }
    public void DeleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {


        private TextView not_title, not_date, not_count;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            not_title = itemView.findViewById(R.id.TitletextView);
            not_date = itemView.findViewById(R.id.DatetextView);
            not_count = itemView.findViewById(R.id.countTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && click != null){
                        click.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }

    }
    public interface ItemClick{
        void onItemClick(DocumentSnapshot documentSnapshot , int position);
    }
    public void setClick(ItemClick click){
        this.click=click;
    }
}


