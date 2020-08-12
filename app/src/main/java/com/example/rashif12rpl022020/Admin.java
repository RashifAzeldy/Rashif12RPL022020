package com.example.rashif12rpl022020;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    Button logoutAsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logoutAsAdmin = (Button)findViewById(R.id.logoutAdmin);

        logoutAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminLogout = new Intent(Admin.this, MainActivity.class);
                startActivity(adminLogout);
            }
        });
    }
}