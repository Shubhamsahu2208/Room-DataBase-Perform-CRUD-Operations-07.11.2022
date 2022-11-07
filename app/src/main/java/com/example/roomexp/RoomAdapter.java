package com.example.roomexp;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private ArrayList<Model> contacts;
    private ClickListener clickListener;


    public RoomAdapter(ArrayList<Model> contact, ClickListener clickListener) {
        this.contacts = contact;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutInflator);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(contacts.get(position).firstName);
        holder.textView2.setText(contacts.get(position).lastName);


        holder.popUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onViewClick(view, position);

            }

        });


    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2;
        ImageView popUpBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            popUpBtn = itemView.findViewById(R.id.iv_more);


        }
    }


}


