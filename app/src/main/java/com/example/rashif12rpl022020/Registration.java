package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.error.ANError;
import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    EditText txtNama, txtEmail, txtPassword, txtNoKTP, txtNoHP, txtAlamat;
    Button btnRegister;
    ProgressDialog progressDialog;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sp = getSharedPreferences("login",MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);

        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtNoKTP = findViewById(R.id.txtNoKTP);
        txtNoHP = findViewById(R.id.txtNoHP);
        txtAlamat = findViewById(R.id.txtAlamat);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = txtNama.getText().toString();
                String email = txtEmail.getText().toString();
                String noKTP = txtNoKTP.getText().toString();
                String noHP = txtNoHP.getText().toString();
                String alamat = txtAlamat.getText().toString();
                String password = txtPassword.getText().toString().trim();
                progressDialog.setTitle("Register In...");
                progressDialog.show();
                AndroidNetworking.post(BaseUrl.url + "login.php")
                        .addBodyParameter("noKTP", noKTP)
                        .addBodyParameter("email", email)
                        .addBodyParameter("password", password)
                        .addBodyParameter("nama", nama)
                        .addBodyParameter("noHP", noHP)
                        .addBodyParameter("alamat", alamat)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject status = response.getJSONObject("STATUS");
                                    JSONObject message = response.getJSONObject("MESSAGE");
                                    Log.d("STATUS", "onResponse: " + status);
                                    if (status.equals("SUCCESS")) {
                                        sp.edit().putBoolean("logged",true).apply();
                                        Intent intent = new Intent(Registration.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(Registration.this, message.toString(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(ANError anError) {
                            }
                        });
                }
            });
        }
    }
}