package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginBedActivity extends AppCompatActivity
{

    ImageView qr_scan;
    public static EditText et_hospital_id;

    EditText et_bed_num,et_bed_id;
    String bed_num,bed_id,hospital_id;
    Button btn_go;
    TextView tv_hospital_beds_availability;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_hospital_id = findViewById(R.id.et_hospital_id);
        et_bed_num = findViewById(R.id.et_room_num);
        et_bed_id = findViewById(R.id.et_room_id);
        qr_scan = findViewById(R.id.img_qr_scan);
        btn_go = findViewById(R.id.btn_go);
        tv_hospital_beds_availability = findViewById(R.id.tv_beds_details);
        progressBar = findViewById(R.id.progress1);
        firebaseAuth= FirebaseAuth.getInstance();




                qr_scan.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(getApplicationContext(), QrScannerActivity.class));
                    }
                });

                btn_go.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        bedLogin();
                    }
                });


                tv_hospital_beds_availability.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        gotoBedDetailsActivity();
                    }
                });

                if (firebaseAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(getApplicationContext(),BedDashBoardActivity.class));
                    finish();
                }
    }

    private void bedLogin()
    {
        progressBar.setVisibility(View.VISIBLE);
        hospital_id = et_hospital_id.getText().toString().trim();
        bed_num = et_bed_num.getText().toString().trim().toLowerCase()+"@room.com";
        bed_id = et_bed_id.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(bed_num, bed_id).addOnCompleteListener(new OnCompleteListener<AuthResult>()

        {

            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    bedDashBoardActivity();
                    Toast.makeText(LoginBedActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginBedActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bedDashBoardActivity()
    {
        Intent intent = new Intent(this, BedDashBoardActivity.class);
        intent.putExtra("hospitalID",hospital_id);
        intent.putExtra("bed_num",et_bed_num.getText().toString().trim().toLowerCase());
        startActivity(intent);
        finish();
    }

    private void gotoBedDetailsActivity()
    {
        startActivity(new Intent(getApplicationContext(), BedDetailsActivity.class));
    }
}