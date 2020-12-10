package com.example.rashif12rpl022020;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardViewSepeda extends RecyclerView.Adapter<CardViewSepeda.UserViewHolder> {

    private ArrayList<ModelSepeda> dataList;
    View viewku;

    public CardViewSepeda(ArrayList<ModelSepeda> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CardViewSepeda.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.activity_card_view_sepeda, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewSepeda.UserViewHolder holder, final int position) {
        holder.textMerk.setText(dataList.get(position).getMerk());
        holder.textJenis.setText(dataList.get(position).getJenis());
        holder.textWarna.setText(dataList.get(position).getWarna());
        holder.textHargaSewa.setText(dataList.get(position).getHargaSewa());

        holder.cvSepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), EditDataSepeda.class);
                intent.putExtra("id", dataList.get(position).getId());
                intent.putExtra("kode", dataList.get(position).getKode());
                intent.putExtra("merk", dataList.get(position).getMerk());
                intent.putExtra("jenis", dataList.get(position).getJenis());
                intent.putExtra("warna", dataList.get(position).getWarna());
                intent.putExtra("hargaSewa", dataList.get(position).getHargaSewa());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView textMerk, textJenis, textWarna, textHargaSewa;
        CardView cvSepeda;

        UserViewHolder(View adapterView) {
            super(adapterView);
            cvSepeda = adapterView.findViewById(R.id.cardVData);
            textMerk = adapterView.findViewById(R.id.txtMerk);
            textJenis = adapterView.findViewById(R.id.txtJenis);
            textWarna = adapterView.findViewById(R.id.txtWarna);
            textHargaSewa = adapterView.findViewById(R.id.txtHargaSewa);
        }
    }
}