package com.example.project_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class ListEdit extends AppCompatActivity {
    EditText nTitle, nContent;
    Calendar c;
    String todaysDate;
    String currentTime;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edytuj");

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        ShopListDB db = new ShopListDB(this);
        ShoppingList shoppingList = db.getList(id);

        final String title = shoppingList.getTitle();
        String content = shoppingList.getContent();
        nTitle = findViewById(R.id.noteTitle);
        nContent = findViewById(R.id.noteDetails);
        nTitle.setText(title);
        nContent.setText(content);

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: " + todaysDate);
        currentTime = pad(c.get(Calendar.HOUR_OF_DAY) + 1) + ":" + pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: " + currentTime);

        FloatingActionButton fabListSaveChanges = findViewById(R.id.fabListSaveChanges);
        fabListSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingList shoppingList = new ShoppingList(id, nTitle.getText().toString(),
                        nContent.getText().toString(), todaysDate, currentTime);
                Log.d("EDITED", "edited: before saving id -> " + shoppingList.getID());
                ShopListDB sDB = new ShopListDB(getApplicationContext());
                long id = sDB.editNote(shoppingList);
                Log.d("EDITED", "EDIT: id " + id);
                goToMain();
                Toast.makeText(getApplicationContext(), "Note Edited.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String pad(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);
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

    private void goToMain() {
        Intent i = new Intent(this, com.example.project_ver1.ui.shopping_list.ShoppingList.class);
        startActivity(i);
    }
}