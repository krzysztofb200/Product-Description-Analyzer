package com.example.project_ver1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddShoppingListActivity extends AppCompatActivity {

    EditText noteProducts, noteTitle;
    Calendar calendar;
    String today, currentTime;
    FloatingActionButton floatingSaveButton, floatingDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        noteProducts = findViewById(R.id.noteProducts);
        noteTitle = findViewById(R.id.noteTitle);
        floatingDeleteButton = findViewById(R.id.floatingDeleteButton);
        floatingSaveButton = findViewById(R.id.floatingSaveButton);

        // Zmiana tytulu nowo otwartej aktywnosci
        setTitle(getResources().getString(R.string.new_shopping_list));

        // Zmienne trzymajace aktualna date i aktualny czas
        calendar = Calendar.getInstance();
        today = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        currentTime = hr(calendar.get(Calendar.HOUR_OF_DAY) + 1) + ":" + hr(calendar.get(Calendar.MINUTE));

        // Dodawanie nowej listy do bazy danych
        floatingSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingList shoppingList = new ShoppingList(noteTitle.getText().toString(), noteProducts.getText().toString(), today, currentTime);
                ShopListDB db = new ShopListDB(AddShoppingListActivity.this);
                db.addList(shoppingList);
                Toast.makeText(AddShoppingListActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Zamykanie aktualnie tworzonej listy zakupow
        floatingDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddShoppingListActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }

    // Zamiana godziny na dwucyfrowa
    private String hr(int i) {
        if(i < 10){
            return  "0" + i;
        }
        else {
            return String.valueOf(i);
        }
    }
}