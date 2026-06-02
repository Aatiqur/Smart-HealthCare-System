package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BedDetails extends AppCompatActivity
{
    EditText et_total_beds,et_available_beds;
    Button btn_update;
    String total_beds,available_beds,hospitalUID;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_total_beds = findViewById(R.id.total);
        et_available_beds = findViewById(R.id.available);
        btn_update = findViewById(R.id.btn_update);
        progressBar = findViewById(R.id.progress5);

        Intent intent = getIntent();
        hospitalUID = intent.getStringExtra("hospitalUID");


        FirebaseDatabase.getInstance().getReference().child("HospitalsBedsDetails").child(hospitalUID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_total_beds.setText(snapshot.child("TotalBeds").getValue().toString());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("HospitalsBedsDetails").child(hospitalUID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_available_beds.setText(snapshot.child("AvailableBeds").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateBedDetails();
            }
        });

    }

    private void updateBedDetails()
    {
        progressBar.setVisibility(View.VISIBLE);
        total_beds = et_total_beds.getText().toString().trim();
        available_beds = et_available_beds.getText().toString().trim();

        HashMap<String, Object> value1 = new HashMap<>();
        value1.put("TotalBeds", total_beds);
        value1.put("AvailableBeds", available_beds);

        FirebaseDatabase.getInstance().getReference().child("HospitalsBedsDetails").child(hospitalUID).updateChildren(value1);
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, AdminDashBoard.class);
        startActivity(intent);
        finish();
    }
    public void onBackPressed()
    {
        Intent intent = new Intent(this, AdminDashBoard.class);
        startActivity(intent);
        finish();
    }
}