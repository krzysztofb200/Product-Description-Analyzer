package com.example.project_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListDetail extends AppCompatActivity {
    long id;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        ShopListDB db = new ShopListDB(this);
        ShoppingList shoppingList = db.getList(id);
        TextView details = findViewById(R.id.noteDesc);
        details.setText(shoppingList.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());

        // Ustawianie tytulu aktualnie otwartej listy na taki, jaki jest w bazie danych
        setTitle(shoppingList.getTitle());

        FloatingActionButton fabListContentDetail = findViewById(R.id.fabListContentDetail);
        fabListContentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopListDB db = new ShopListDB(getApplicationContext());
                db.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Lista usunięta",Toast.LENGTH_SHORT).show();
                goToMain();
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

    private void goToMain() {
        Intent i = new Intent(this, com.example.project_ver1.ui.shopping_list.ShoppingList.class);
        startActivity(i);
    }
}
