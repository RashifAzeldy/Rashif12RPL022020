package com.example.rashif12rpl022020;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

public class InsertDataSepeda extends SuperclassInsert {

    private EditText editMerk, editKode, editHargaSewa, editJenis, editWarna;
    private ImageView img;
    private Button insert, insertImg;
    private ImageView back;
    SharedPreferences sharedPreferences;

    private Bitmap mSelectedImage;
    private String mSelectedImagePath;
    File mSelectedFileBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_sepeda);

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);

        editMerk = findViewById(R.id.txtMerk);
        editWarna = findViewById(R.id.txtWarna);
        editKode = findViewById(R.id.txtKode);
        editHargaSewa = findViewById(R.id.txtHargaSewa);
        editJenis = findViewById(R.id.txtJenis);
        img = findViewById(R.id.image);
        insert = findViewById(R.id.btnInsert);
        insertImg = findViewById(R.id.btnImage);
        insertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(InsertDataSepeda.this);
                new PickSetup().setCameraButtonText("Gallery");
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String kode = editKode.getText().toString();
                final String warna = editWarna.getText().toString();
                final String merk = editMerk.getText().toString();
                final String hargaSewa = editHargaSewa.getText().toString();
                final String jenis = editJenis.getText().toString();

                if (kode.isEmpty()){
                    Toast.makeText(InsertDataSepeda.this,"Kode tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }

                if (merk.isEmpty()){
                    Toast.makeText(InsertDataSepeda.this,"Merk tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }

                if (warna.isEmpty()){
                    Toast.makeText(InsertDataSepeda.this,"Warna tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }

                if (hargaSewa.isEmpty()){
                    Toast.makeText(InsertDataSepeda.this,"Harga Sewa tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }

                if (jenis.isEmpty()){
                    Toast.makeText(InsertDataSepeda.this,"Jenis tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
                HashMap<String, String> body = new HashMap<>();
                body.put("kode", kode);
                body.put("merk", merk);
                body.put("warna", warna);
                body.put("hargaSewa", hargaSewa);
                body.put("jenis", jenis);

                AndroidNetworking.upload(BaseURL.url+"Insert.php")
                        .addMultipartFile("gambar",mSelectedFileBanner)
                        .addMultipartParameter(body)
                        .setPriority(Priority.MEDIUM)
                        .setOkHttpClient(((Init) getApplication()).getOkHttpClient())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                JSONObject hasil = response.optJSONObject("hasil");
                                String respon = hasil.optString("respon");
                                Log.d("STATUS", "onResponse: " + hasil);
                                if (respon.equals("sukses")) {
                                    Intent intent = new Intent(InsertDataSepeda.this, AdminActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(InsertDataSepeda.this, "gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("TAG", "onError: " + anError.getErrorDetail());
                                Log.d("TAG", "onError: " + anError.getErrorBody());
                                Log.d("TAG", "onError: " + anError.getErrorCode());
                                Log.d("TAG", "onError: " + anError.getResponse());

                                Toast.makeText(InsertDataSepeda.this, "Add Data Gagal", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    @Override
    public void onPickResult(PickResult r) {
        if(r.getError() == null){
            try {
                File file = new Compressor(this)
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(new File(r.getPath()));
                mSelectedImagePath = file.getAbsolutePath();
                mSelectedFileBanner = new File(mSelectedImagePath);
                mSelectedImage=r.getBitmap();
                img.setImageBitmap(mSelectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(InsertDataSepeda.this, r.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}