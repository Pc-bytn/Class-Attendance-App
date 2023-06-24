package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StudentLoginActivity extends AppCompatActivity {
    EditText email, psw;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        email = findViewById(R.id.emailLogin);
        psw = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.stdLoginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonPressed();
            }
        });


    }

    public void loginButtonPressed(){
        Intent i = new Intent(getApplicationContext(), StudentHomepageActivity.class);
        startActivity(i);
    }
}