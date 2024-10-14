package com.example.findrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuList extends AppCompatActivity {

    TextView res_name;
    RecyclerView recycler;

    FirebaseAuth auth;
    FirebaseDatabase database;
    String id;
    Last_adapter adapter;
    ArrayList<Menu> menuArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        res_name=findViewById(R.id.res_name);
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(MenuList.this));
        id=getIntent().getStringExtra("id");

        DatabaseReference ref= database.getReference().child("User").child(id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User users=new User();
                users=snapshot.getValue(User.class);
                res_name.setText(users.res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("restaurant").child(id).child("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Menu menus=new Menu();
                    menus=dataSnapshot.getValue(Menu.class);
                    menuArrayList.add(menus);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);

        adapter=new Last_adapter(menuArrayList,MenuList.this);
        recycler.setAdapter(adapter);


    }
}