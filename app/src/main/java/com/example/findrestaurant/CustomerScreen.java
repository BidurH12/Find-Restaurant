package com.example.findrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.credentials.webauthn.Cbor;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.media.RouteListingPreference;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerScreen extends AppCompatActivity {


    RecyclerView recycler;
   FirebaseAuth auth;
   FirebaseDatabase database;
   restaurant_adapter ad;
   SearchView search;
   ArrayList<User> userList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(CustomerScreen.this));
        auth=FirebaseAuth.getInstance();
        search=findViewById(R.id.search);
        search.clearFocus();
        database=FirebaseDatabase.getInstance();



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }
        });


        DatabaseReference ref=database.getReference().child("User");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=new User();
                    user=dataSnapshot.getValue(User.class);
                    user.setRes_ID(dataSnapshot.getKey());
                    userList.add(user);
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);

        ad=new restaurant_adapter(userList,CustomerScreen.this);
        recycler.setAdapter(ad);

    }

    public void filterlist(String text){
        ArrayList<User> filteredList=new ArrayList<>();
        for(User item:userList){
            if(item.getRes().toLowerCase().contains(text.toLowerCase())|| item.getAdd().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
            ad.setFilteredList(filteredList);

    }
}