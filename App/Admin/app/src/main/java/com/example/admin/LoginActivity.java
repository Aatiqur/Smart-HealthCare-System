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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{

    String hospital_username_email,hospital_ID;
    EditText et_hospital_username,et_hospital_ID;
    TextView tv_register;
    Button btn_login;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btn_login = findViewById(R.id.btn_add);
        tv_register = findViewById(R.id.tv_register);
        et_hospital_ID = findViewById(R.id.et_room_id);
        et_hospital_username = findViewById(R.id.et_room_number);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress3);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginAdmin();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoRegisterActivity();
            }
        });



    }

    private void gotoAdminDashBoardActivity()
    {
        Intent intent = new Intent(this, AdminDashBoard.class);
        intent.putExtra("hospitalUID2",firebaseAuth.getUid());
        startActivity(intent);
        finish();
    }
    private void gotoRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginAdmin()
    {
        progressBar.setVisibility(View.VISIBLE);
        hospital_username_email = et_hospital_username.getText().toString().toLowerCase().trim()+"@hospital.com";
        hospital_ID = et_hospital_ID.getText().toString().trim();


        firebaseAuth.signInWithEmailAndPassword(hospital_username_email, hospital_ID).addOnCompleteListener(new OnCompleteListener<AuthResult>()

        {

            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    gotoAdminDashBoardActivity();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}