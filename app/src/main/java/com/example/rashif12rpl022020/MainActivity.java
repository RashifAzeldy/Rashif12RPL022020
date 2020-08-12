package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.txtusername);
        password = (EditText)findViewById(R.id.txtpassword);
        loginButton = (Button) findViewById(R.id.btnlogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().equals("admin") && password.getText().equals("admin123")){
                    // Logged in as Admin
                    Intent admin = new Intent(MainActivity.this, Admin.class);
                    startActivity(admin);
                }else{
                    // Logged in as Customer
                    Intent customer = new Intent(MainActivity.this, CustomerActivity.class);
                    startActivity(customer);
                }
            }
        });
    }
}