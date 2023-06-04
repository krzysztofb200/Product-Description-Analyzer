package com.example.project_ver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project_ver1.ui.all_products.AllProducts;
import com.example.project_ver1.ui.all_products.AllProductsRVAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchByBarcodeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    AllProductsRVAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_search_by_barcode);

        Intent i = getIntent();

        String newText = i.getStringExtra("barcode");
        setTitle(newText);

        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference().child("products");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_barcode_product);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<AllProducts> options
                = new FirebaseRecyclerOptions.Builder<AllProducts>()
                .setQuery(mbase.orderByChild("bar_code").startAt(newText).endAt(newText + "\uf8ff"), AllProducts.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new AllProductsRVAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    public void onResume(){
        super.onResume();
        Intent i = getIntent();

        String newText = i.getStringExtra("barcode");
        mbase = FirebaseDatabase.getInstance().getReference().child("products");

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<AllProducts> options
                = new FirebaseRecyclerOptions.Builder<AllProducts>()
                .setQuery(mbase.orderByChild("bar_code").startAt(newText).endAt(newText + "\uf8ff"), AllProducts.class)
                .build();
        adapter = new AllProductsRVAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    // Opcja pozwalajaca cofnac sie do poprzedniego okna (wszystkie listy zakupow)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}