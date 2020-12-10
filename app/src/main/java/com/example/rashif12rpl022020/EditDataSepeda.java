package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditDataSepeda extends AppCompatActivity {

    TextView tvId;
    EditText etKode, etMerk, etJenis, etWarna, etHargaSewa;
    Button btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_sepeda);

        tvId = findViewById(R.id.tvIdDataSepeda);
        etKode = findViewById(R.id.txtKode);
        etMerk = findViewById(R.id.txtMerk);
        etJenis = findViewById(R.id.txtJenis);
        etWarna = findViewById(R.id.txtWarna);
        etHargaSewa = findViewById(R.id.txtHargaSewa);
        btnEdit = findViewById(R.id.btnEditDataSepeda);
        btnDelete = findViewById(R.id.btnDeleteDataSepeda);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String kode = extras.getString("kode");
        final String merk = extras.getString("merk");
        final String jenis = extras.getString("jenis");
        final String warna = extras.getString("warna");
        final String hargaSewa = extras.getString("hargaSewa");

        tvId.setText("Id :" + id);
        etKode.setText(kode);
        etMerk.setText(merk);
        etJenis.setText(jenis);
        etWarna.setText(warna);
        etHargaSewa.setText(hargaSewa);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post(BaseURL.url + "UpdateDataSepeda.php")
                        .addBodyParameter("id", id)
                        .addBodyParameter("kode", etKode.getText().toString())
                        .addBodyParameter("merk", etMerk.getText().toString())
                        .addBodyParameter("jenis", etJenis.getText().toString())
                        .addBodyParameter("warna", etWarna.getText().toString())
                        .addBodyParameter("hargaSewa", etHargaSewa.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("onResponse", "Edited");

                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(EditDataSepeda.this, SepedaList.class);
                                        returnIntent.putExtra("refresh", "refresh");
                                        startActivity(returnIntent);
                                        finish();
                                        Toast.makeText(EditDataSepeda.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditDataSepeda.this, "Edit gagal", Toast.LENGTH_SHORT).show();
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(BaseURL.url + "DeleteDataSepeda.php")
                        .addBodyParameter("id", id)
                        .addBodyParameter("kode", etKode.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("onResponse", "Edited");

                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(EditDataSepeda.this, SepedaList.class);
                                        startActivity(returnIntent);
                                        finish();
                                        Toast.makeText(EditDataSepeda.this, "Delete Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditDataSepeda.this, "Delete gagal", Toast.LENGTH_SHORT).show();
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
}