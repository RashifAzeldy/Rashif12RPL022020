package com.example.rashif12rpl022020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<ModelUser> rentalArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {

            @Override
            public void run() {
                getDataFromRemote();
            }
        });
    }

    private void getDataFromRemote() {
        refreshLayout.setRefreshing(true);
        AndroidNetworking.post(BaseURL.url + "GetDataCustomer.php")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        refreshLayout.setRefreshing(false);
                        Log.d("hasiljson", "onResponse: " + response.toString());

                        rentalArraylist = new ArrayList<>();
                        try {
                            Log.d("hasiljson", "onResponse: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("result");
                            Log.d("hasiljson2", "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("jsonobject", "onResponse: " + jsonObject);
                                ModelUser item = new ModelUser();

                                item.setId(jsonObject.optString("id"));
                                item.setRoleUser(jsonObject.optString("roleUser"));
                                item.setEmail(jsonObject.optString("email"));
                                item.setNama(jsonObject.optString("nama"));
                                item.setNoKTP(jsonObject.optString("noKTP"));
                                item.setNoHP(jsonObject.optString("noHP"));
                                item.setAlamat(jsonObject.optString("alamat"));
                                rentalArraylist.add(item);
                            }

                            adapter = new Adapter(rentalArraylist);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DataListActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        refreshLayout.setRefreshing(false);
                        Log.d("error", "onError errorCode : " + error.getErrorCode());
                        Log.d("error", "onError errorBody : " + error.getErrorBody());
                        Log.d("error", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            getDataFromRemote();
        }
    }

    @Override
    public void onRefresh() {
        getDataFromRemote();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Go Back?")
                .setMessage("Are you sure you want to Go Back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(DataListActivity.this, AdminActivity.class);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }
}
