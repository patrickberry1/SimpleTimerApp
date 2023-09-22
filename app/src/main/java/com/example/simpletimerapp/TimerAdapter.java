package com.example.simpletimerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerViewHolder> {

    Context context;
    List<Timer> timers;
    OnClickListener onClickListener;
    private OnItemDeleteListener listener;

    public interface OnItemDeleteListener{
        void OnItemClick(int position);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener deleteListener){
        listener = deleteListener;
    }

    public TimerAdapter(Context context, List<Timer> timers) {
        this.context = context;
        this.timers = timers;
    }

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimerViewHolder(LayoutInflater.from(context).inflate(R.layout.timer_view, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {
        Timer timer = timers.get(position);
        holder.itemView.setId(timers.get(position).id);
        holder.textView.setText(timers.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder.getAbsoluteAdapterPosition(), timer);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return timers.size();
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Timer timer);
    }
}
