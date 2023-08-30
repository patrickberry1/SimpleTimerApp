package com.example.simpletimerapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    //TODO
//    ImageButton delete_button;
//    ImageButton edit_button;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.timer_view_timer_name);
        //TODO
//        delete_button = itemView.findViewById(R.id.timer_view_delete_button);
//        edit_button = itemView.findViewById(R.id.timer_edit_image_button);
    }

}
