package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerActivity extends AppCompatActivity {

    Button logoutAsCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        logoutAsCustomer = (Button)findViewById(R.id.logoutCustomer);

        logoutAsCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(CustomerActivity.this, MainActivity.class);
                startActivity(logout);
            }
        });
    }
}