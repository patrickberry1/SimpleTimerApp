package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView timer_list_rec_view;
    FloatingActionButton add_timer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //temporary static list of timers
        List<Timer> timers = new ArrayList<Timer>();
        timers.add(new Timer("10/3 Repeaters", Arrays.asList(new Step[]{new Step(0, 10), new Step(0, 3), new Step(0, 10), new Step(0, 3), new Step(0, 10)})));

        //set up recycler view
        timer_list_rec_view = findViewById(R.id.timer_list_rec_view);
        timer_list_rec_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final MyAdapter adapter = new MyAdapter(getApplicationContext(), timers);
        timer_list_rec_view.setAdapter(adapter);
        adapter.setOnClickListener(new MyAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Timer timer){
                Intent intent = new Intent(MainActivity.this, StartTimerActivity.class);
                intent.putExtra("Name", timer.name);
                intent.putExtra("Steps",(Serializable) timer.stepList);
                startActivity(intent);
            }
        });

        //set up add timer button
        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTimerActivity.class);
                startActivity(intent);
            }
        });

    }
}