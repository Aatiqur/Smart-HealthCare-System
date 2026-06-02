package com.kazimasum.qrdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myAdapter extends FirebaseRecyclerAdapter<DataModel,myAdapter.myViewHolder>
{

    Context context;
    String hospitalName,totalBeds,availableBeds;
    DatabaseReference databaseReference;


    public myAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options, Context context)
    {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull  myAdapter.myViewHolder holder,final int position, @NonNull  DataModel model)
    {
        hospitalName = model.getHospitalName();
        totalBeds = model.getTotalBeds();
        availableBeds = model.getAvailableBeds();

        holder.tv_hospitalName.setText(hospitalName);
        holder.tv_totalBeds.setText(totalBeds);
        holder.tv_availableBeds.setText(availableBeds);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_hospitalName,tv_totalBeds,tv_availableBeds;
        public myViewHolder(@NonNull  View itemView)
        {
            super(itemView);
            tv_hospitalName = itemView.findViewById(R.id.tv_hospital_name);
            tv_totalBeds = itemView.findViewById(R.id.tv_total_beds);
            tv_availableBeds = itemView.findViewById(R.id.tv_available_beds);

        }
    }
}
