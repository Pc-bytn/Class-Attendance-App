package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructorSignActivity extends AppCompatActivity {

    Button instructorLogin, instructorSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign);

        instructorLogin = findViewById(R.id.btn_InstructorLogin);
        instructorSignup = findViewById(R.id.btn_TeacherRegister);


        instructorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InstructorLoginActivity.class);
                startActivity(i);
            }
        });

        instructorSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), InstructorSignupActivity.class);
                startActivity(i2);
            }
        });
    }
}