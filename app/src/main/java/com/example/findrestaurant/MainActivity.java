package com.example.findrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ComponentCaller;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    Button Add;
    RecyclerView recycler;
    ImageView food, exit;
    ArrayList<Menu> menu = new ArrayList<>();
    Menu_Adapter adapter;
    TextView res_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        res_name = findViewById(R.id.res_name);
        exit = findViewById(R.id.exit);
        Add = findViewById(R.id.Add);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Log Out");
                alert.setMessage("Do you sure want to Log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        Intent next = new Intent(MainActivity.this, Log_SignUp.class);
                        startActivity(next);
                        finish();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });


        DatabaseReference ref = database.getReference().child("User").child(auth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = new User();
                user = snapshot.getValue(User.class);
                res_name.setText(user.res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, add_activity.class);
                startActivity(intent);


            }

        });

        DatabaseReference reference = database.getReference().child("restaurant").child(auth.getCurrentUser().getUid()).child("food");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menu.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Menu menu1 = new Menu();
                    menu1 = dataSnapshot.getValue(Menu.class);
                    menu1.setFoodId(dataSnapshot.getKey());
                    menu.add(menu1);
                }
               adapter.set(menu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);

        adapter = new Menu_Adapter(MainActivity.this, menu);
        recycler.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}

