package com.example.findrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Last_adapter extends RecyclerView.Adapter<Last_adapter.ViewHolder> {
    ArrayList<Menu>  menuArrayList=new ArrayList<>();
    Context context;

    public Last_adapter(ArrayList<Menu> menuArrayList, Context context) {
        this.menuArrayList = menuArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Last_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Last_adapter.ViewHolder holder, int position) {
        holder.name.setText(menuArrayList.get(position).item);
        holder.amt.setText(menuArrayList.get(position).price);
        Glide.with(context)
                .load(menuArrayList.get(position).image)
                .into(holder.item);

    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item;
        TextView name;
        TextView amt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            name=itemView.findViewById(R.id.name);
            amt=itemView.findViewById(R.id.amt);
        }
    }
}
