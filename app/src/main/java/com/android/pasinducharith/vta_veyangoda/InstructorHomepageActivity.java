package com.android.pasinducharith.vta_veyangoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InstructorHomepageActivity extends AppCompatActivity {

    private EditText wbname;

    public String changingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_homepage);

        wbname = findViewById(R.id.wbname);
        Button sendName = findViewById(R.id.sendName);


        sendName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                thread.start();
            }
        });

    }

    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                changingName = wbname.getText().toString();
                copyAndRenameSheet(changingName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    public void copyAndRenameSheet(String newName) {
        try {
            URL url = new URL("https://script.google.com/macros/s/AKfycbz55-19DUBOhKZLqzOnIC2DE19YUQwCtC4eryrGR_y_VKWFfB_cMYDG0GRpDtn3PI8ymA/exec?newName=" + URLEncoder.encode(newName, "UTF-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                changingName = wbname.getText().toString();
                Toast.makeText(this, "Successfully created the " + changingName + " batch google sheet document", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Couldn't create the google sheet document for new batch", Toast.LENGTH_SHORT).show();
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}