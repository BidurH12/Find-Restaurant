package com.example.findrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Log_SignUp extends AppCompatActivity {

    Button register,log;

    FirebaseDatabase database;
    TextView guest;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_up);
        register=findViewById(R.id.register);
        log=findViewById(R.id.log);
        guest=findViewById(R.id.guest);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        if(auth.getCurrentUser()!=null){
            Intent jump=new Intent(Log_SignUp.this,MainActivity.class);
            startActivity(jump);
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(Log_SignUp.this,SignUp.class);
                startActivity(next);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Log_SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guest1=new Intent(Log_SignUp.this,CustomerScreen.class);
                startActivity(guest1);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}