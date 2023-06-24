package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//new Imports
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable; //updated one
import androidx.appcompat.app.AppCompatActivity; //updated one

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class StudentSignupActivity extends AppCompatActivity {

    EditText fName, nameWithInitials, address, nicNo, phoneNo, email, pswFirst, pswSecond;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        fName = findViewById(R.id.fullName);
        nameWithInitials = findViewById(R.id.initialName);
        address = findViewById(R.id.address);
        nicNo = findViewById(R.id.nic);
        phoneNo = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        pswFirst = findViewById(R.id.passwordFirst);
        pswSecond = findViewById(R.id.passwordSecond);

        btnRegister = findViewById(R.id.stdRegisterbtn);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToSheet();
            }
        });

    }

    public void addItemToSheet() {
        //Data sending Method

        String stdFname = fName.getText().toString().trim();
        String stdNameWithInitials = nameWithInitials.getText().toString().trim();
        String stdAddress = address.getText().toString().trim();
        String stdnicNo = nicNo.getText().toString().trim();
        String stdPhoneNo = phoneNo.getText().toString().trim();
        String stdEmail = email.getText().toString().trim();
        String stdFirstPsw = pswFirst.getText().toString().trim();
        String stdSecondpsw = pswSecond.getText().toString().trim();


        if (!stdFname.equals("") && !stdNameWithInitials.equals("")
                && !stdAddress.equals("") && !stdnicNo.equals("")
                && !stdPhoneNo.equals("") && !stdEmail.equals("")
                && stdFirstPsw.equals(stdSecondpsw)) {
            String psw = stdSecondpsw;

            final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait");

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "https://script.google.com/macros/s/AKfycbwHd8vRD23VUhiDk5uzUiuEcOp-7-hWzvH98Qo99qc8Wr2OQev1HMAy83VsaILDC2s7/exec",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    Toast.makeText(StudentSignupActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> parmas = new HashMap<>();
                    //here we pass params
                    parmas.put("action", "addStudent");
                    parmas.put("stdFame", stdFname);
                    parmas.put("stdNameWithInitials", stdNameWithInitials);
                    parmas.put("stdAddress", stdAddress);
                    parmas.put("stdnicNo", stdnicNo);
                    parmas.put("stdPhoneNo", stdPhoneNo);
                    parmas.put("stdEmail", stdEmail);
                    parmas.put("psw", psw);

                    return parmas;
                }
            };

            int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue queue = Volley.newRequestQueue(this);

            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Password Is Not Matching Or All Fields Not Filled", Toast.LENGTH_SHORT).show();
        }


    }


}