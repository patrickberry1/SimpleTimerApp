package com.example.simpletimerapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimerViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    public TimerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.timer_view_timer_name);
    }

}
