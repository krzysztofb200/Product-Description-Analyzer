package com.example.project_ver1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    LayoutInflater inflater;
    static List<ShoppingList> lists;

    public RVAdapter(Context context, List<ShoppingList> lists){
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.custom_shopping_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.ViewHolder viewHolder, int i) {
        String title = lists.get(i).getTitle();
        String date = lists.get(i).getDate();
        String time = lists.get(i).getTime();
        viewHolder.nTitle.setText(title);
        viewHolder.nDate.setText(date);
        viewHolder.nTime.setText(time);

        String link = "https://firebasestorage.googleapis.com/v0/b/project-ver1-7d8d2.appspot.com/o/cart.jpeg?alt=media&token=950f7956-713f-4073-ad55-481363412b83";
        Picasso.get().load(link).into(ViewHolder.imageView2);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle, nDate, nTime;
        static ImageView imageView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),ListDetail.class);
                    i.putExtra("ID",lists.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
