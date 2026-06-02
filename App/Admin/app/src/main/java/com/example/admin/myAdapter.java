package com.example.admin;

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
    String all_bed_numbers,hospitalUID;
    DatabaseReference databaseReference;


    public myAdapter(@NonNull  FirebaseRecyclerOptions<DataModel> options,Context context)
    {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull  myAdapter.myViewHolder holder,final int position, @NonNull  DataModel model)
    {

        all_bed_numbers = model.getBedNumber();
        hospitalUID = model.getHospitalUID();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUID).child("Beds");
        databaseReference.keepSynced(true);
        final String bed_key = getRef(position).getKey();
        holder.tv_bed_number.setText("Bed Number - "+ all_bed_numbers);


        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, EditBedActivity.class);
                intent.putExtra("BedNumber", bed_key);
                intent.putExtra("hospitalUID",hospitalUID);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_bed_number;
        CardView cardView;
        public myViewHolder(@NonNull  View itemView)
        {
            super(itemView);
            tv_bed_number = itemView.findViewById(R.id.tv_bed_number);
            cardView = itemView.findViewById(R.id.cv_card_view);
        }
    }
}
