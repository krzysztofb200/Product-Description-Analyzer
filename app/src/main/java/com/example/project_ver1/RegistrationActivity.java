package com.example.project_ver1;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView, confirmPassword, usern;
    private Button btnRegister, btnLoginActivity;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Rejestracja");

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        btnRegister = findViewById(R.id.btnregister);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        progressbar = findViewById(R.id.progressbar);
        confirmPassword = findViewById(R.id.passwdConfirm);
        usern = findViewById(R.id.username);

        // Set on Click Listener on Registration button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });

        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerNewUser(){
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password, confPasswd, username;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        confPasswd = confirmPassword.getText().toString();
        username = usern.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),
                            "Wpisz nazwę użytkownika", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Wpisz adres email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Wpisz hasło", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confPasswd)) {
            Toast.makeText(getApplicationContext(),
                            "Potwierdź hasło", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confPasswd)) {
            Toast.makeText(getApplicationContext(),
                            "Podane hasła się nie zgadzają", Toast.LENGTH_LONG).show();
            return;
        }


        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Rejestracja udana", Toast.LENGTH_LONG).show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(RegistrationActivity.this,
                                    MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Rejestracja się nie udała"
                                                    + " Spróbuj ponownie później",
                                            Toast.LENGTH_LONG).show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}