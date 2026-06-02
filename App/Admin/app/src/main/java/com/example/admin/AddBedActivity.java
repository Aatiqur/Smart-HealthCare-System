package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddBedActivity extends AppCompatActivity
{

    String hospitalUID;

    String room_num,room_id;
    Button btn_add;
    EditText et_roomNum,et_roomID;

    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_add = findViewById(R.id.btn_add);
        et_roomNum = findViewById(R.id.et_room_number);
        et_roomID = findViewById(R.id.et_room_id);
        firebaseAuth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress4);



        Intent intent = getIntent();
        hospitalUID = intent.getStringExtra("hospitalUID");

        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addBed();
            }
        });

    }

    private void addBed()
    {
        progressBar.setVisibility(View.VISIBLE);
        room_num = et_roomNum.getText().toString().trim().toLowerCase()+"@room.com";
        room_id = et_roomID.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(room_num,room_id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            HashMap<String, Object> value1 = new HashMap<>();
                            value1.put("BedNumber", et_roomNum.getText().toString().trim().toLowerCase());
                            value1.put("BedID", room_id);
                            value1.put("HospitalUID", hospitalUID);
                            value1.put("DoctorName", "");
                            value1.put("DoctorPhone", "");
                            value1.put("PatientName", "");
                            value1.put("PatientPhone", "");
                            value1.put("PatientAge", "");
                            value1.put("PatientGender", "");
                            value1.put("HeartRate", "");
                            value1.put("BodyTemperature", "");
                            value1.put("OxygenSaturation", "");
                            
                            FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalUID).child("Beds").child(et_roomNum.getText().toString().trim().toLowerCase()).updateChildren(value1);
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(AddBedActivity.this, AdminDashBoard.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Log.e("Err","The Error is"+task.getException().getMessage());
                            Toast.makeText(AddBedActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }
    public void onBackPressed()
    {
        Intent intent = new Intent(this, AdminDashBoard.class);
        startActivity(intent);
        finish();
    }
}