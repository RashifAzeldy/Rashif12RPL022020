package com.example.rashif12rpl022020;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.RentalViewHolder> {

    private ArrayList<ModelUser> datalist;

    public Adapter(ArrayList<ModelUser> datalist) {
        this.datalist = datalist;
    }

    @Override
    public RentalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new RentalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RentalViewHolder holder, final int position) {

        holder.email.setText(datalist.get(position).getEmail());
        holder.nama.setText(datalist.get(position).getNama());
        holder.nohp.setText(datalist.get(position).getNoHP());
        holder.alamat.setText(datalist.get(position).getAlamat());
        holder.noktp.setText(datalist.get(position).getNoKTP());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.itemView.getContext(), EditCustomer.class);
                in.putExtra("id", datalist.get(position).getId());
                in.putExtra("nama", datalist.get(position).getNama());
                in.putExtra("email", datalist.get(position).getEmail());
                in.putExtra("nohp", datalist.get(position).getNoHP());
                in.putExtra("alamat", datalist.get(position).getAlamat());
                in.putExtra("noktp", datalist.get(position).getNoKTP());
                holder.itemView.getContext().startActivity(in);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (datalist != null) ? datalist.size() : 0;
    }


    class RentalViewHolder extends RecyclerView.ViewHolder {
        private TextView email, nama, nohp, alamat, noktp;
        CardView card;

        RentalViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.cardVData);
            email = (TextView) itemView.findViewById(R.id.itemEmail);
            nama = (TextView) itemView.findViewById(R.id.itemNama);
            nohp = (TextView) itemView.findViewById(R.id.itemNoHP);
            alamat = (TextView) itemView.findViewById(R.id.itemAlamat);
            noktp = (TextView) itemView.findViewById(R.id.itemNoKTP);
        }
    }
}
