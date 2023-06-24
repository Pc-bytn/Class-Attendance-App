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


public class StudentLoginActivity extends AppCompatActivity {
    EditText email, psw;
    Button loginBtn;

    FirebaseAuth fAuth;

    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        email = findViewById(R.id.emailLogin);
        psw = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.stdLoginBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonPressed();
            }
        });


    }

    public void loginButtonPressed(){

        String email1 = email.getText().toString().trim();
        String psw1 = psw.getText().toString().trim();

        if (!email1.equals("") && !psw1.equals("")){
            fAuth.signInWithEmailAndPassword(email1,psw1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(StudentLoginActivity.this, "Logging Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StudentHomepageActivity.class));
                    finish();
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StudentLoginActivity.this, "Logging failed", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(getApplicationContext(), StudentHomepageActivity.class));
            finish();
        }
    }
}