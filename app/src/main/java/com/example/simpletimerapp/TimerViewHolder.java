package com.example.simpletimerapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimerViewHolder extends RecyclerView.ViewHolder {

    View rootView;
    TextView textView;
    Button deleteButton;
    DatabaseHelper DB;

    public TimerViewHolder(@NonNull View itemView, TimerAdapter.OnItemDeleteListener listener) {
        super(itemView);
        rootView = itemView;
        textView = itemView.findViewById(R.id.timer_view_timer_name);
        deleteButton = itemView.findViewById(R.id.timer_view_delete_button);
        DB = new DatabaseHelper(itemView.getContext().getApplicationContext());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo", "Delete for user: " + itemView.getId());
                DB.deleteTimer(itemView.getId());
                listener.OnItemClick(getAbsoluteAdapterPosition());
            }
        });
    }

}
