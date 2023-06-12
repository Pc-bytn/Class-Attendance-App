package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button studentBtn, instructorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentBtn = findViewById(R.id.btn_Student);
        instructorBtn = findViewById(R.id.btn_Instructor);

        //Call student sign in and up activity when pressed studentBtn
        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_callStudent = new Intent(getApplicationContext(), StudentSignActivity.class);
                startActivity(intent_callStudent);
            }
        });

        //Call instructor sign in and up activity when pressed instructorBtn
        instructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_CallInstructor = new Intent(getApplicationContext(), InstructorSignActivity.class);
                startActivity(intent_CallInstructor);
            }
        });
    }
}