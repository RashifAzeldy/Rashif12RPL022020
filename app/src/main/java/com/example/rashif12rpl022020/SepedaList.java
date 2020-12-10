package com.example.rashif12rpl022020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SepedaList extends AppCompatActivity {

    private RecyclerView recView;
    private CardViewSepeda cvSepeda;

    ArrayList<ModelSepeda> dataList;
    CardView cView;

    TextView tvKode, tvMerk, tvJenis, tvWarna, tvHargaSewa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepeda_list);

        cView = findViewById(R.id.cardVData);
        tvKode = findViewById(R.id.txtKode);
        tvMerk = findViewById(R.id.txtMerk);
        tvJenis = findViewById(R.id.txtJenis);
        tvWarna = findViewById(R.id.txtWarna);
        tvHargaSewa = findViewById(R.id.txtHargaSewa);
        recView = findViewById(R.id.listSepeda);

        dataList = new ArrayList<>();

        Log.d("Response", "onCreate: ");

        AndroidNetworking.post(BaseURL.url + "GetDataSepeda.php")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("result");

                            Log.d("OnResponse", "Data Sepeda View");
                            for (int i = 0; i < data.length(); i++) {

                                ModelSepeda modelSepeda = new ModelSepeda();
                                JSONObject object = data.getJSONObject(i);
                                modelSepeda.setId(object.getString("id"));
                                modelSepeda.setKode(object.getString("kode"));
                                modelSepeda.setMerk(object.getString("merk"));
                                modelSepeda.setJenis(object.getString("jenis"));
                                modelSepeda.setWarna(object.getString("warna"));
                                modelSepeda.setHargaSewa(object.getString("hargaSewa"));
                                dataList.add(modelSepeda);

                            }

                            cvSepeda = new CardViewSepeda(dataList);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SepedaList.this);

                            recView.setLayoutManager(layoutManager);

                            recView.setAdapter(cvSepeda);

                            Log.d("pay1", "onResponse: " + response.getJSONArray("result"));
                            Log.d("Response", "Length: " + dataList.size());

                            if (response.getJSONArray("result").length() == 0){
                                recView.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("geo", "onResponse: " + anError.toString());
                        Log.d("geo", "onResponse: " + anError.getErrorBody());
                        Log.d("geo", "onResponse: " + anError.getErrorCode());
                        Log.d("geo", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }
}