package com.example.project_ver1;

import android.util.Log;
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

public class PromoCodesRVAdapter extends FirebaseRecyclerAdapter<PromoCodes, PromoCodesRVAdapter.PromoCodesViewHolder> {
    public PromoCodesRVAdapter(@NonNull FirebaseRecyclerOptions<PromoCodes> options) {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull PromoCodesViewHolder holder,
                     int position, @NonNull PromoCodes model) {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.firstname.setText(model.getFirstname());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.lastname.setText(model.getLastname());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.age.setText(model.getAge());

        String link = model.getImage();
        Picasso.get().load(link).into(PromoCodesViewHolder.rImage);
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public PromoCodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_promo_codes_list_view, parent, false);
        return new PromoCodesViewHolder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    public static class PromoCodesViewHolder extends RecyclerView.ViewHolder {
        TextView firstname, lastname, age;
        static ImageView rImage;
        public PromoCodesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            age = itemView.findViewById(R.id.age);
            rImage = (ImageView) itemView.findViewById(R.id.rImage);
        }
    }
}
