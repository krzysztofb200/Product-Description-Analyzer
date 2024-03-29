package com.example.project_ver1.ui.promo_codes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_ver1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Set;

public class PromoCodesDetail extends AppCompatActivity {
    DatabaseReference mbase;
    TextView brand_detail, expires_detail, code_detail, description_detail, link_detail;
    ImageView image_detail;
    //String image_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_codes_detail);

        brand_detail = findViewById(R.id.brand_detail);
        expires_detail = findViewById(R.id.expires_detail);
        code_detail = findViewById(R.id.code_detail);
        description_detail = findViewById(R.id.description_detail);
        link_detail = findViewById(R.id.link_detail);
        image_detail = (ImageView) findViewById(R.id.image_detail);
        link_detail.setMovementMethod(LinkMovementMethod.getInstance());

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String id = i.getStringExtra("ID");
        Log.d("ID", "ID: " + id);
        String brand = i.getStringExtra("Brand");
        String expires = i.getStringExtra("Expires");
        String code = i.getStringExtra("Code");
        setTitle(brand);

        brand_detail.setText(brand);
        expires_detail.setText(expires);
        code_detail.setText(code);

        mbase = FirebaseDatabase.getInstance().getReference().child("promo_codes");

        mbase.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {

            // TODO: Set error condition in case any of the fields is empty
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    description_detail.setText(dataSnapshot1.child("desc").getValue().toString());

                    String image_link = dataSnapshot1.child("image").getValue().toString();
                    Log.d("Image", "Image url: " + dataSnapshot1.child("image").getValue().toString());
                    Picasso.get().load(image_link).into(image_detail);

                    link_detail.setText(dataSnapshot1.child("link").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Opcja pozwalajaca cofnac sie do poprzedniego okna (wszystkie listy zakupow)
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