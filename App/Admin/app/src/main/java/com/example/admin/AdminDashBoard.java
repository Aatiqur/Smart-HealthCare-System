package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashBoard extends AppCompatActivity {

    String hospitalUID;
    SharedPreferences sharedPreferences;

    Button btn_addBed, btn_editBed, btn_bedDetails, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_addBed = findViewById(R.id.btn_add_bed);
        btn_editBed = findViewById(R.id.btn_edit_bed);
        btn_bedDetails = findViewById(R.id.btn_bed_details);
        btn_logout = findViewById(R.id.btn_logout);


        sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
        Intent intent = getIntent();
        String hospitalUID1 = intent.getStringExtra("hospitalUID1");
        String hospitalUID2 = intent.getStringExtra("hospitalUID2");
        if (hospitalUID1 != null)
        {
            storeHospitalUID(hospitalUID1);
        }
        else if (hospitalUID2 != null)
        {
            storeHospitalUID(hospitalUID2);
        }

        hospitalUID = getStoredHospitalUID();


        btn_addBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddBedActivity();
            }


        });

        btn_editBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAllBedsActivity();
            }
        });

        btn_bedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoBedDetailsActivity();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOut();
            }
        });



    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void gotoAddBedActivity() {
        Intent intent = new Intent(this, AddBedActivity.class);
        intent.putExtra("hospitalUID", hospitalUID);
        startActivity(intent);
        finish();
    }

    private void gotoAllBedsActivity() {
        Intent intent = new Intent(this, AllBedsActivity.class);
        intent.putExtra("hospitalUID", hospitalUID);
        startActivity(intent);
        finish();
    }

    private void gotoBedDetailsActivity() {
        Intent intent = new Intent(this, BedDetails.class);
        intent.putExtra("hospitalUID", hospitalUID);
        startActivity(intent);
        finish();
    }

    private void storeHospitalUID(String hospitalUID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hospitalUID", hospitalUID);
        editor.apply();
    }

    private String getStoredHospitalUID()
    {
        String value = sharedPreferences.getString("hospitalUID", "Save a note");
        return value;
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashBoard.this);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure to Logout");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminDashBoard.this, LoginActivity.class);
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