package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nokhba.nokhbahmd.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.not_count.setText(String.valueOf(position+1));

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView not_title,not_msg,not_date,not_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            not_title = itemView.findViewById(R.id.TitletextView);
            not_msg = itemView.findViewById(R.id.MsgtextView);
            not_date = itemView.findViewById(R.id.DatetextView);
            not_count = itemView.findViewById(R.id.countTextView);
        }
    }
}
