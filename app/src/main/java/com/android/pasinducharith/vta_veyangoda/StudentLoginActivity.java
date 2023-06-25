package com.android.pasinducharith.vta_veyangoda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class StudentLoginActivity extends AppCompatActivity {
    EditText emailStd, pswStd;
    Button loginBtnStd, forgotPswStd;

    FirebaseAuth fAuth;

    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        emailStd = findViewById(R.id.emailLogin);
        pswStd = findViewById(R.id.passwordLogin);
        loginBtnStd = findViewById(R.id.stdLoginBtn);
        forgotPswStd = findViewById(R.id.studentForgetPsw);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        loginBtnStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonPressed();
            }
        });

        forgotPswStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPswAction();
            }
        });

    }

    private void forgotPswAction() {
        String frEmail = emailStd.getText().toString().trim();

        if (!frEmail.equals("")) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(frEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginButtonPressed(){

        String email1 = emailStd.getText().toString().trim();
        String psw1 = pswStd.getText().toString().trim();

        if (!email1.equals("") && !psw1.equals("")){
            fAuth.signInWithEmailAndPassword(email1,psw1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    studentLogin(authResult.getUser().getUid());

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

    private void studentLogin(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("errors", "onSuccess: " +documentSnapshot.getData());
                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(), StudentHomepageActivity.class));
                    finish();
                }

                if (documentSnapshot.getString("isAdmin") != null){
                    Toast.makeText(StudentLoginActivity.this, "You are not a Student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}