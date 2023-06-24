package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentSignActivity extends AppCompatActivity {

    Button studentLogin, studentSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign);

        studentLogin = findViewById(R.id.btn_StudentLogin);
        studentSignup = findViewById(R.id.btn_StudentRegister);

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StudentLoginActivity.class);
                startActivity(i);
            }
        });

        studentSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), StudentSignupActivity.class);
                startActivity(i2);
            }
        });

    }
}