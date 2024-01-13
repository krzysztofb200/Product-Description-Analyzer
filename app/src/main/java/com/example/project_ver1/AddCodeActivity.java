package com.example.project_ver1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ver1.ui.all_products.AllProducts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class AddCodeActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    StorageReference storageReference;

    // creating a variable for
    // our object class
    AllCodes allCodes;
    EditText etProductName, etProductDescription, etBarCode, etLink, etDay, etMonth, etYear;
    TextView etProductID;
    Button btnAddProduct, btnAddPhoto;
    Uri imageUri;
    ImageView ivProductImage;
    Bitmap bmp;
    ByteArrayOutputStream baos;
    byte[] byteArray;
    int numberOfProducts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.add_code));

        etBarCode = findViewById(R.id.etBarCode);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductName = findViewById(R.id.etProductName);
        etProductID = findViewById(R.id.etProductID);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        ivProductImage = findViewById(R.id.ivProductImage);
        etLink = findViewById(R.id.link);
        etYear = findViewById(R.id.etYear);
        etDay = findViewById(R.id.etDay);
        etMonth = findViewById(R.id.etMonth);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("promo_codes");
        storageReference = firebaseStorage.getReference("promo_codes");

        // initializing our object
        // class variable.
        allCodes = new AllCodes();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numberOfProducts = (int) snapshot.getChildrenCount();
                etProductID.setText(Integer.toString(numberOfProducts + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddCodeActivity.this, "Nie udalo sie obliczyc liczby kodow", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etProductDescription.getText().toString().isEmpty() || etProductName.getText().toString().isEmpty() || etProductName.getText().toString().isEmpty() || ivProductImage.getDrawable() == null){
                    Toast.makeText(AddCodeActivity.this, "Wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
                } else if (!isDateValid(etDay.getText().toString(), etMonth.getText().toString(), etYear.getText().toString())) {
                    Toast.makeText(AddCodeActivity.this, "Data nie jest poprawna", Toast.LENGTH_SHORT).show();
                } else{
                    uploadPicture();
                }
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }

    private void uploadPicture() {

        final String randomUUID = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child(randomUUID);

        riversRef.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String brand = etProductName.getText().toString();
                        String code = etBarCode.getText().toString();
                        String desc = etProductDescription.getText().toString();
                        String id = etProductID.getText().toString();
                        String image = String.valueOf(uri);
                        String link = etLink.getText().toString();
                        String day = etDay.getText().toString();
                        String month = etMonth.getText().toString();
                        String year = etYear.getText().toString();
                        String expires = day + "/" + month + "/" + year;
                        addDatatoFirebase(brand, code, desc, id, image, link, expires);
                        onBackPressed();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCodeActivity.this, "Nie udalo sie dodac kodu", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            //creating byte array of photo to compress it
            bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            baos = new ByteArrayOutputStream();

            // here we can choose quality factor
            // in third parameter(ex. here it is 25)
            bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);

            byteArray = baos.toByteArray();

            ivProductImage.setImageURI(imageUri);
        }
    }

    private void addDatatoFirebase(String brand, String code, String desc, String id, String image, String link, String expires) {
        // below 3 lines of code is used to set
        // data in our object class.
        allCodes.setBrand(brand);
        allCodes.setCode(code);
        allCodes.setDesc(desc);
        allCodes.setID(id);
        allCodes.setImage(image);
        allCodes.setLink(link);
        allCodes.setExpires(expires);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child("promo" + id).setValue(allCodes);

                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "Kod dodany", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Nie udalo sie dodac " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isDateValid(String day, String month, String year){
        Calendar c;
        c = Calendar.getInstance();
        int d = Integer.valueOf(day);
        int m = Integer.valueOf(month);
        int y = Integer.valueOf(year);
        if(d <= 31 && d >= 1 && m >= 1 && m <= 12 && y >= c.get(Calendar.YEAR)){
            return true;
        } else {
            return false;
        }
    }
}
