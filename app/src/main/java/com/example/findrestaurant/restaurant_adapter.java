package com.example.findrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class restaurant_adapter extends RecyclerView.Adapter<restaurant_adapter.ViewHolder> {

    ArrayList<User> userList=new ArrayList<>();
    Context context;

    public void setFilteredList(ArrayList<User> filteredList){
        this.userList=filteredList;
        notifyDataSetChanged();
    }


    restaurant_adapter(ArrayList<User> userList,Context context){
        this.userList=userList;
        this.context=context;
    }
    @NonNull
    @Override
    public restaurant_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull restaurant_adapter.ViewHolder holder, int position) {
        User users=userList.get(position);
        holder.name.setText(userList.get(position).res);
        holder.address.setText(userList.get(position).add);

        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resId=users.getRes_ID();

                Intent intent=new Intent(context, MenuList.class);
                intent.putExtra("id",resId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,address;
        RelativeLayout relative;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            relative=itemView.findViewById(R.id.relative);
        }
    }
}
