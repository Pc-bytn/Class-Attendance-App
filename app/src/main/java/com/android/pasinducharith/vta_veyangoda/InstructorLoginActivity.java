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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InstructorLoginActivity extends AppCompatActivity {

    EditText email, psw;

    Button teacherLoginBtn, pswForgot;

    FirebaseAuth fAuth;

    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_login);

        email = findViewById(R.id.emailLoginIns);
        psw = findViewById(R.id.passwordLoginIns);
        pswForgot = findViewById(R.id.teacherForgetpsw);

        teacherLoginBtn = findViewById(R.id.teacherLoginBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        teacherLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorLogingbuttonPressed();
            }
        });

        pswForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPswAction();
            }
        });
    }

    private void forgotPswAction() {

        String frEmail = email.getText().toString().trim();

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

    private void instructorLogingbuttonPressed() {

        String email1 = email.getText().toString().trim();
        String psw1 = psw.getText().toString().trim();

        if (!email1.equals("") && !psw1.equals("")) {
            fAuth.signInWithEmailAndPassword(email1, psw1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    teacherLogin(authResult.getUser().getUid());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InstructorLoginActivity.this, "Logging failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
        }


    }

    private void teacherLogin(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("errors", "onSuccess: " + documentSnapshot.getData());
                if (documentSnapshot.getString("isUser") != null) {
                    Toast.makeText(InstructorLoginActivity.this, "You are not an Instructor", Toast.LENGTH_SHORT).show();

                }

                if (documentSnapshot.getString("isAdmin") != null) {
                    startActivity(new Intent(getApplicationContext(), InstructorHomepageActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.getString("isAdmin") != null) {
                        startActivity(new Intent(getApplicationContext(), InstructorHomepageActivity.class));
                        finish();
                    }

                    if (documentSnapshot.getString("isUser") != null) {
                        Toast.makeText(InstructorLoginActivity.this, "You are logged in as a User", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), InstructorSignActivity.class));
                    finish();
                }
            });

        }
    }
}