package com.example.project_ver1.ui.all_products;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ver1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AllProductsDetail extends AppCompatActivity {
    DatabaseReference mbase;
    TextView name_detail, code_detail, description_detail;
    ImageView image_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_detail);

        name_detail = findViewById(R.id.name_detail);
        code_detail = findViewById(R.id.code_detail);
        description_detail = findViewById(R.id.description_detail);
        image_detail = (ImageView) findViewById(R.id.image_detail);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String id = i.getStringExtra("ID");
        Log.d("ID", "ID: " + id);
        String name = i.getStringExtra("Name");
        String code = i.getStringExtra("Code");
        setTitle(name);

        name_detail.setText(name);
        code_detail.setText(code);

        mbase = FirebaseDatabase.getInstance().getReference().child("products");

        mbase.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    description_detail.setText(dataSnapshot1.child("desc").getValue().toString());
                    String image_link = dataSnapshot1.child("image").getValue().toString();
                    Log.d("Image", "Image url: " + dataSnapshot1.child("image").getValue().toString());
                    Picasso.get().load(image_link).into(image_detail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Opcja pozwalajaca cofnac sie do poprzedniego okna
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
