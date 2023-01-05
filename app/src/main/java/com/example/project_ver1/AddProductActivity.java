package com.example.project_ver1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    AllProducts allProducts;
    EditText etProductName, etProductDescription, etBarCode, etProductImage, etProductID;
    Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etBarCode = findViewById(R.id.etBarCode);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductName = findViewById(R.id.etProductName);
        etProductImage = findViewById(R.id.etProductImage);
        etProductID = findViewById(R.id.etProductID);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("products");

        // initializing our object
        // class variable.
        allProducts = new AllProducts();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etProductName.getText().toString();
                String bar_code = etBarCode.getText().toString();
                String desc = etProductDescription.getText().toString();
                String id = etProductID.getText().toString();
                String image = etProductImage.getText().toString();

                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(bar_code) && TextUtils.isEmpty(desc)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(getApplicationContext(), "Wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(name, bar_code, desc, id, image);
                }
            }
        });
    }

    private void addDatatoFirebase(String name, String bar_code, String desc, String id, String image) {
        // below 3 lines of code is used to set
        // data in our object class.
        allProducts.setName(name);
        allProducts.setBar_code(bar_code);
        allProducts.setDesc(desc);
        allProducts.setID(id);
        allProducts.setImage(image);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(name).setValue(allProducts);

                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "Produkt dodany", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}