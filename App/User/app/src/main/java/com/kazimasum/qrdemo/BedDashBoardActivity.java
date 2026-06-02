package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BedDashBoardActivity extends AppCompatActivity {


    String hospitalID;


    String dr_name, dr_phn, pt_name, pt_age, pt_phn, heart_rate, spo2, temp, bed_num;
    TextView tv_dr_name, tv_dr_phn, tv_pt_name, tv_pt_age, tv_pt_phn, tv_heart_rate, tv_spo2, tv_temp, tv_bed_num;
    Button btn_logout;
    ImageView img_dr_call, img_pt_call;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_dash_board);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_bed_num = findViewById(R.id.bed_num);
        tv_dr_name = findViewById(R.id.doctor_name);
        tv_dr_phn = findViewById(R.id.doctor_phn);
        tv_pt_name = findViewById(R.id.patient_name);
        tv_pt_age = findViewById(R.id.patient_age);
        tv_pt_phn = findViewById(R.id.patient_phn);
        tv_heart_rate = findViewById(R.id.bpm);
        tv_spo2 = findViewById(R.id.spo);
        tv_temp = findViewById(R.id.tmp);
        img_dr_call = findViewById(R.id.doctor_call);
        img_pt_call = findViewById(R.id.patient_call);
        btn_logout = findViewById(R.id.logout);
        progressBar = findViewById(R.id.progressbar4);


        Intent intent = getIntent();
        String hospitalUID = intent.getStringExtra("hospitalID");
        String bed_number = intent.getStringExtra("bed_num");
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (hospitalUID != null) {
            editor.putString("id", hospitalUID);
            editor.apply();
        }
        if (bed_number != null) {
            editor.putString("bed", bed_number);
            editor.apply();
        }

        String h = sp.getString("id", null);
        String b = sp.getString("bed", null);

        if (h != null || b != null) {
            hospitalID = h;
            bed_num = b;

        }

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                String bedNumber = snapshot.child("BedNumber").getValue().toString();
                progressBar.setVisibility(View.GONE);
                tv_bed_num.setText("#" + bedNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dr_name = snapshot.child("DoctorName").getValue().toString();
                tv_dr_name.setText(dr_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dr_phn = snapshot.child("DoctorPhone").getValue().toString();
                tv_dr_phn.setText(dr_phn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pt_name = snapshot.child("PatientName").getValue().toString();
                tv_pt_name.setText(pt_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pt_age = snapshot.child("PatientAge").getValue().toString();
                tv_pt_age.setText("Age: " + pt_age);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pt_phn = snapshot.child("PatientPhone").getValue().toString();
                tv_pt_phn.setText(pt_phn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                heart_rate = snapshot.child("HeartRate").getValue().toString();
                tv_heart_rate.setText(heart_rate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                spo2 = snapshot.child("OxygenSaturation").getValue().toString();
                tv_spo2.setText(spo2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospitalID).child("Beds").child(bed_num).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                temp = snapshot.child("BodyTemperature").getValue().toString();
                tv_temp.setText(temp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        img_dr_call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               String doctor_number = "tel:"+dr_phn;
               Intent intent1 = new Intent((Intent.ACTION_DIAL));
               intent1.setData(Uri.parse(doctor_number));
               startActivity(intent1);
            }
        });

        img_pt_call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_number = "tel:"+pt_phn;
                Intent intent2 = new Intent((Intent.ACTION_DIAL));
                intent2.setData(Uri.parse(patient_number));
                startActivity(intent2);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                logOut();
            }
        });

    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void logOut()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(BedDashBoardActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure to Logout");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(BedDashBoardActivity.this, LoginBedActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}