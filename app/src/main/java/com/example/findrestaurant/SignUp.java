package com.example.findrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText restaurant,mail,password,add;
    Button btn;
    FirebaseDatabase database;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        restaurant=findViewById(R.id.restaurant);
        mail=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        add=findViewById(R.id.add);
        btn=findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Restaurant = restaurant.getText().toString();
                String Email = mail.getText().toString();
                String Password = password.getText().toString();
                String Add = add.getText().toString();

                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(Restaurant, Email, Password, Add);
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("User").child(id).setValue(user);
                                Toast.makeText(SignUp.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

    }
}