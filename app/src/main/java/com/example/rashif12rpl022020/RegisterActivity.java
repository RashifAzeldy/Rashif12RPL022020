package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText etemail;
    EditText etpassword;
    EditText etnama;
    EditText etnohp;
    EditText etalamat;
    EditText etnoktp;

    SharedPreferences sharedPreferences;

    private Button btnRegis;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        etemail = findViewById(R.id.regEmail);
        etpassword = findViewById(R.id.regPassword);
        etnama = findViewById(R.id.regNama);
        etnohp = findViewById(R.id.regNoHP);
        etalamat = findViewById(R.id.regAlamat);
        etnoktp = findViewById(R.id.regNoKTP);

        btnRegis = findViewById(R.id.btnRegisterR);
        btnLogin = findViewById(R.id.btnLoginR);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String nama = etnama.getText().toString();
                String nohp = etnohp.getText().toString();
                String alamat = etalamat.getText().toString();
                String noktp = etnoktp.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Email Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                if (nama.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Nama Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                if (nohp.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "No.HP Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                if (alamat.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Alamat Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                if (noktp.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "No.KTP Harus Di Isi !", Toast.LENGTH_SHORT).show();
                }
                AndroidNetworking.post(BaseURL.url + "Register.php")
                        .addBodyParameter("email", email)
                        .addBodyParameter("password", password)
                        .addBodyParameter("nama", nama)
                        .addBodyParameter("noHP", nohp)
                        .addBodyParameter("alamat", alamat)
                        .addBodyParameter("noKTP", noktp)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean respon = hasil.getBoolean("respon");
                                    Log.d("STATUS", "onResponse: " + hasil);
                                    if (respon) {
                                        sharedPreferences.edit().putString("logged", "customer").apply();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("TAG", "onError: " + anError.getErrorDetail());
                                Log.d("TAG", "onError: " + anError.getErrorBody());
                                Log.d("TAG", "onError: " + anError.getErrorCode());
                                Log.d("TAG", "onError: " + anError.getResponse());

                                Toast.makeText(RegisterActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Register?")
                .setMessage("Are you sure you want to Go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(RegisterActivity.this, MainActivity.class);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }
}