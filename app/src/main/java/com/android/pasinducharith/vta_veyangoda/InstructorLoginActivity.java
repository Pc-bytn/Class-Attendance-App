package com.android.pasinducharith.vta_veyangoda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class InstructorLoginActivity extends AppCompatActivity {

    EditText email, psw;

    Button teacherLoginBtn;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_login);

        email = findViewById(R.id.emailLoginIns);
        psw = findViewById(R.id.passwordLoginIns);

        teacherLoginBtn = findViewById(R.id.teacherLoginBtn);

        fAuth = FirebaseAuth.getInstance();


        teacherLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorLogingbuttonPressed();
            }
        });
    }

    private void instructorLogingbuttonPressed() {

        String email1 = email.getText().toString().trim();
        String psw1 = psw.getText().toString().trim();

        if (!email1.equals("") && !psw1.equals("")){
            fAuth.signInWithEmailAndPassword(email1,psw1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(InstructorLoginActivity.this, "Logging Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InstructorHomepageActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InstructorLoginActivity.this, "Logging failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), InstructorHomepageActivity.class));
            finish();
        }
    }
}