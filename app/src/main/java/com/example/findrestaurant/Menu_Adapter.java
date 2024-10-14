package com.example.findrestaurant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Menu_Adapter extends RecyclerView.Adapter<Menu_Adapter.ViewHolder> {

    Context context;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    ArrayList<Menu> menu=new ArrayList<>();

    Menu_Adapter(Context context,ArrayList<Menu> menu){
        this.context=context;
        this.menu=menu;
    }


    @NonNull
    @Override
    public Menu_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Menu_Adapter.ViewHolder holder, int position) {
        Menu menu1=menu.get(position);
        holder.name.setText(menu.get(position).item);
        holder.amt.setText(menu.get(position).price);
        Glide.with(context)
                .load(menu.get(position).image)
                .into(holder.item);


        holder.relative.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setMessage("Do you sure want to Delete this item?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id=menu1.getFoodId();
                        FirebaseDatabase.getInstance().getReference().child("restaurant").child(auth.getCurrentUser().getUid())
                                .child("food").child(id).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item;
        TextView name;
        TextView amt;
        RelativeLayout relative;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            name=itemView.findViewById(R.id.name);
            amt=itemView.findViewById(R.id.amt);
            relative=itemView.findViewById(R.id.relative);

        }
    }

    public void set(ArrayList<Menu> menu){
        this.menu=menu;
        notifyDataSetChanged();
    }
}
