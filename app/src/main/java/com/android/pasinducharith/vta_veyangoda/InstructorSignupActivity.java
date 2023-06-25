package com.android.pasinducharith.vta_veyangoda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InstructorSignupActivity extends AppCompatActivity {

    EditText fullName, initName, subject, email, passwordFirst, passwordSecond;
    Button registerInstructor;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_signup);
        fullName = findViewById(R.id.insFullName);
        initName = findViewById(R.id.insInitialName);
        subject = findViewById(R.id.course);
        email = findViewById(R.id.insEmail);
        passwordFirst = findViewById(R.id.insPasswordFirst);
        passwordSecond = findViewById(R.id.insPasswordSecond);

        registerInstructor = findViewById(R.id.insRegister);


        registerInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorRegister();
            }
        });


    }

    private void instructorRegister() {
        String fname = fullName.getText().toString().trim();
        String initname = initName.getText().toString().trim();
        String subjt = subject.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String fPsw = passwordFirst.getText().toString().trim();
        String sPsw = passwordSecond.getText().toString().trim();

        if (!fname.equals("") && !initname.equals("")
                && !subjt.equals("") && !mail.equals("")
                && fPsw.equals(sPsw)) {

            loading = new ProgressDialog(InstructorSignupActivity.this);
            loading.setMessage("Registering");
            loading.setCancelable(false);
            loading.show();

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            fAuth.createUserWithEmailAndPassword(mail, fPsw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(InstructorSignupActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();


                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("fullName", fname);
                    userInfo.put("nameWithInitials", initname);
                    userInfo.put("subject", subjt);
                    userInfo.put("isAdmin", "1");

                    df.set(userInfo);


                    if (!isFinishing() && loading.isShowing()) {
                        loading.dismiss();
                    }

                    startActivity(new Intent(getApplicationContext(), InstructorHomepageActivity.class));
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InstructorSignupActivity.this, "Failed to register.", Toast.LENGTH_SHORT).show();
                    if (!isFinishing() && loading.isShowing()) {
                        loading.dismiss();
                    }
                }
            });


        }


    }
}