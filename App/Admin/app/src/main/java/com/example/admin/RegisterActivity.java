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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    String username_email,id,name;
    EditText   et_name,et_username,et_id;
    Button btn_register;
    TextView tv_login;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_register = findViewById(R.id.btn_register);
        tv_login=findViewById(R.id.tv_login);
        et_name= findViewById(R.id.et_hospital_name);
        et_username= findViewById(R.id.et_room_number);
        et_id = findViewById(R.id.et_room_id);
        progressBar = findViewById(R.id.progress1);
        firebaseAuth=FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerUser();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoLoginActivity();
            }
        });
        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), AdminDashBoard.class));
            finish();
        }


    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void gotoAdminDashBoardActivity()
    {
        Intent intent = new Intent(this, AdminDashBoard.class);
        intent.putExtra("hospitalUID1",firebaseAuth.getUid());
        startActivity(intent);
        finish();
    }

    private void gotoLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerUser()
    {
        name = et_name.getText().toString().trim().toUpperCase();
        username_email = et_username.getText().toString().trim().toLowerCase()+"@hospital.com";
        id = et_id.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(username_email,id)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                           {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task)
                               {
                                   if (task.isSuccessful())
                                   {
                                       HashMap<String, Object> value1 = new HashMap<>();
                                       value1.put("HospitalName", name);
                                       value1.put("HospitalUserName", username_email);
                                       value1.put("HospitalID", id);
                                       value1.put("HospitalUID",firebaseAuth.getUid());
                                       FirebaseDatabase.getInstance().getReference().child("Hospitals").child(firebaseAuth.getUid()).updateChildren(value1);

                                       HashMap<String, Object> value2 = new HashMap<>();
                                       value2.put("HospitalName", name);
                                       value2.put("HospitalUserName", username_email);
                                       value2.put("HospitalID", id);
                                       value2.put("TotalBeds", "--");
                                       value2.put("HospitalUID",firebaseAuth.getUid());
                                       value2.put("AvailableBeds", "--");
                                       FirebaseDatabase.getInstance().getReference().child("HospitalsBedsDetails").child(firebaseAuth.getUid()).updateChildren(value2);
                                       gotoAdminDashBoardActivity();
                                       progressBar.setVisibility(View.GONE);
                                   }
                                   else
                                   {
                                       progressBar.setVisibility(View.GONE);
                                       Log.e("Err","The Error is"+task.getException().getMessage());
                                       Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                }

    }
