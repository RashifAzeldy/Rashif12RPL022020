package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;

public class EditCustomer extends AppCompatActivity {

    EditText edEmail, edNama, edNoHP, edAlamat, edNoKTP;
    Button btnedit, btndelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        edEmail = findViewById(R.id.editEmail);
        edNama = findViewById(R.id.editNama);
        edNoHP = findViewById(R.id.editNoHP);
        edAlamat = findViewById(R.id.editAlamat);
        edNoKTP = findViewById(R.id.editNoKTP);
        btnedit = findViewById(R.id.btn_edit);

        Bundle extras = getIntent().getExtras();
        final String Id = extras.getString("id");
        final String email = extras.getString("email");
        final String nama = extras.getString("nama");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");
        final String noktp = extras.getString("noktp");

        edNama.setText(nama);
        edEmail.setText(email);
        edNoHP.setText(nohp);
        edAlamat.setText(alamat);
        edNoKTP.setText(noktp);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> body = new HashMap<>();
                body.put("id", Id);
                body.put("nama", edNama.getText().toString());
                body.put("nohp", edNoHP.getText().toString());
                body.put("noktp", edNoKTP.getText().toString());
                body.put("email", edEmail.getText().toString());
                body.put("alamat", edAlamat.getText().toString());

                AndroidNetworking.post(BaseURL.url + "Update.php")
                        .addBodyParameter(body)
                        .setPriority(Priority.MEDIUM)
                        .setOkHttpClient(((Init) getApplication()).getOkHttpClient())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("TAG", "respon: " + response);
                                String message = response.optString("message");

                                Toast.makeText(EditCustomer.this, message, Toast.LENGTH_LONG).show();
                                Log.d("AS", "onResponse: " + message);
                                if (message.equalsIgnoreCase("success")) {
                                    Toast.makeText(EditCustomer.this, "Update Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(EditCustomer.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("A", "onError: " + anError.getErrorBody());
                                Log.d("A", "onError: " + anError.getLocalizedMessage());
                                Log.d("A", "onError: " + anError.getErrorDetail());
                                Log.d("A", "onError: " + anError.getResponse());
                                Log.d("A", "onError: " + anError.getErrorCode());

                            }
                        });
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(BaseURL.url + "deleteCostumer.php")
                        .addBodyParameter("id", Id)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("onResponse" , "Edited");

                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(EditCustomer.this, DataListActivity.class);
                                        startActivity(returnIntent);
                                        finish();
                                        Toast.makeText(EditCustomer.this, "Delete Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditCustomer.this, "Delete Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("OnResponse", "No Connection");
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Editing?")
                .setMessage("Are you sure you want to cancel Editing?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(EditCustomer.this, AdminActivity.class);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }
}