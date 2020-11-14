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

import java.util.HashMap;

public class EditCustomer extends AppCompatActivity {

    EditText textemail, txtnama, txtnohp, txtalamat, txtnoktp;
    Button btnedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        textemail = findViewById(R.id.editEmail);
        txtnama = findViewById(R.id.editNama);
        txtnohp = findViewById(R.id.editNoHP);
        txtalamat = findViewById(R.id.editAlamat);
        txtnoktp = findViewById(R.id.editNoKTP);
        btnedit = findViewById(R.id.btn_edit);

        Bundle extras = getIntent().getExtras();
        final String Id = extras.getString("id");
        final String email = extras.getString("email");
        final String nama = extras.getString("nama");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");
        final String noktp = extras.getString("noktp");

        txtnama.setText(nama);
        textemail.setText(email);
        txtnohp.setText(nohp);
        txtalamat.setText(alamat);
        txtnoktp.setText(noktp);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> body = new HashMap<>();
                body.put("id", Id);
                body.put("nama", txtnama.getText().toString());
                body.put("nohp", txtnohp.getText().toString());
                body.put("noktp", txtnoktp.getText().toString());
                body.put("email", textemail.getText().toString());
                body.put("alamat", txtalamat.getText().toString());

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