package com.android.pasinducharith.vta_veyangoda;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class InstructorHomepageActivity extends AppCompatActivity {

    TextView etNameShow;
    Button logout, resetAttendanceBtn;

    FirebaseFirestore fStore;

    FirebaseAuth fAuth;

    FirebaseUser fUser;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_homepage);

        logout = findViewById(R.id.logOutInstructor);
        resetAttendanceBtn = findViewById(R.id.resetAttendance);
        etNameShow = findViewById(R.id.nameShow);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        resetAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = new ProgressDialog(InstructorHomepageActivity.this);
                loading.setMessage("Resetting The Attendance Records...");
                loading.setCancelable(false);
                loading.show();

                resetAttendanceAction();
            }
        });
    }

    private void resetAttendanceAction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbwUqp88vv0w3QwSCOCxXMhIShwLu4R1fQywlKZsi6EOqjHjf_bkDjC2VEy0iWAWN1YuKw/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(InstructorHomepageActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // here we pass params
                params.put("action", "resetAttendance");
                return params;
            }
        };

        int socketTimeOut = 50000; // u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null) {
            String userId = fUser.getUid();
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(userId);
            userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w("errors", "Listen failed.", error);
                        return;
                    }
                    if (value != null && value.exists()) {
                        String nameWithI = value.getString("nameWithInitials");
                        assert nameWithI != null;
                        etNameShow.setText(nameWithI.toString());
                    }
                }
            });
        }
    }
}