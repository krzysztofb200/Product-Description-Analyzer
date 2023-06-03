package com.example.project_ver1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AllProductsRVAdapter extends FirebaseRecyclerAdapter<AllProducts, AllProductsRVAdapter.AllProductsViewHolder> {
    public AllProductsRVAdapter(@NonNull FirebaseRecyclerOptions<AllProducts> options) {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull AllProductsRVAdapter.AllProductsViewHolder holder,
                                    int position, @NonNull AllProducts model) {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.name.setText(model.getName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.bar_code.setText(model.getBar_code());

        holder.id.setText(model.getID());

        String link = model.getImage();
        Picasso.get().load(link).fit().centerCrop().into(AllProductsRVAdapter.AllProductsViewHolder.rImage);
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public AllProductsRVAdapter.AllProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_all_products_list_view, parent, false);
        return new AllProductsRVAdapter.AllProductsViewHolder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    public static class AllProductsViewHolder extends RecyclerView.ViewHolder {
        TextView name, bar_code, id;
        static ImageView rImage;
        public AllProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.brand);
            bar_code = itemView.findViewById(R.id.code);
            rImage = (ImageView) itemView.findViewById(R.id.rImage);
            id = itemView.findViewById(R.id.id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), AllProductsDetail.class);
                    i.putExtra("ID",id.getText().toString());
                    i.putExtra("Name", name.getText().toString());
                    i.putExtra("Code", bar_code.getText().toString());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}

