package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditBedActivity extends AppCompatActivity
{
    EditText et_pt_name,et_pt_age,et_pt_gender,et_pt_phone,et_dr_name,et_dr_phone;
    String pt_name,pt_age,pt_gender,pt_phone,dr_name,dr_phone,bedNumber,hospitalUid;
    Button btn_publish,btn_release;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_pt_name = findViewById(R.id.et_patient_name);
        et_pt_age = findViewById(R.id.et_patient_age);
        et_pt_gender = findViewById(R.id.et_patient_gender);
        et_pt_phone = findViewById(R.id.et_patient_guardians_phone);
        et_dr_name = findViewById(R.id.et_doctor_name);
        et_dr_phone = findViewById(R.id.et_doctor_phone);
        btn_publish = findViewById(R.id.btn_publish);
        btn_release = findViewById(R.id.btn_release_patient);

        Intent intent = getIntent();
        bedNumber = intent.getStringExtra("BedNumber");
        hospitalUid = intent.getStringExtra("hospitalUID");

        getData();

        btn_publish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                uploadData();
            }
        });

        btn_release.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                blankData();
            }
        });

    }

    private void blankData()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditBedActivity.this);
        builder.setTitle("Release Patient");
        builder.setMessage("Are You Sure to Release The Patient?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                HashMap<String, Object> value1 = new HashMap<>();
                value1.put("PatientName", "");
                value1.put("PatientAge", "");
                value1.put("PatientGender", "");
                value1.put("PatientPhone","");
                value1.put("DoctorName","");
                value1.put("DoctorPhone","");
                FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).updateChildren(value1);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.show();

    }

    private void uploadData()
    {
        pt_name = et_pt_name.getText().toString().trim();
        pt_age = et_pt_age.getText().toString().trim();
        pt_gender = et_pt_gender.getText().toString().trim();
        pt_phone = et_pt_phone.getText().toString().trim();
        dr_name = et_dr_name.getText().toString().trim();
        dr_phone = et_dr_phone.getText().toString().trim();

        HashMap<String, Object> value1 = new HashMap<>();
        value1.put("PatientName", pt_name);
        value1.put("PatientAge", pt_age);
        value1.put("PatientGender", pt_gender);
        value1.put("PatientPhone",pt_phone);
        value1.put("DoctorName",dr_name);
        value1.put("DoctorPhone",dr_phone);
        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).updateChildren(value1);
    }

    private void getData()
    {
        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_pt_name.setText(snapshot.child("PatientName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_pt_age.setText(snapshot.child("PatientAge").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_pt_gender.setText(snapshot.child("PatientGender").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_pt_phone.setText(snapshot.child("PatientPhone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_dr_name.setText(snapshot.child("DoctorName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUid).child("Beds").child(bedNumber).addValueEventListener(new ValueEventListener()        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                et_dr_phone.setText(snapshot.child("DoctorPhone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });



    }


}