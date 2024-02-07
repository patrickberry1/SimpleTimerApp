package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView timer_list_rec_view;
    FloatingActionButton add_timer_button;
    DatabaseHelper DB;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZE STUFF
        DB = new DatabaseHelper(getApplicationContext());
        constraintLayout = findViewById(R.id.main_activity_cl);


        //TEMP STATIC TIMERS
        //timers.add(new Timer("10/3 Repeaters", Arrays.asList(new Step[]{new Step(0, 10), new Step(0, 3), new Step(0, 10), new Step(0, 3), new Step(0, 10)})));


        //TEMP DB STUFF
        //String temp_structure = "{\"steps\":[{\"mins\": 0, \"secs\": 7}, {\"mins\":0, \"secs\":3}]}";
        //TODO: delete all timers


        //DB GET
        List<Timer> timers = new ArrayList<Timer>();
        Cursor res = DB.getData();
        while(res.moveToNext()){
            int id = res.getInt(0);
            String name = res.getString(1);
            String structure = res.getString(2);
            List<Step> steps = getStepsFromStructure(structure);
            if (steps != null){
                timers.add(new Timer(name, steps, id));
            }
        }

        //SET UP RECYCLER VIEW
        timer_list_rec_view = findViewById(R.id.timer_list_rec_view);
        timer_list_rec_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final TimerAdapter adapter = new TimerAdapter(getApplicationContext(), timers);
        timer_list_rec_view.setAdapter(adapter);
        adapter.setOnClickListener(new TimerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Timer timer){
                Intent intent = new Intent(MainActivity.this, StartTimerActivity.class);
                intent.putExtra("Name", timer.name);
                intent.putExtra("Steps",(Serializable) timer.stepList);
                intent.putExtra("id", timer.id);
                startActivity(intent);
            }
        });
        adapter.setOnItemDeleteListener(new TimerAdapter.OnItemDeleteListener() {
            @Override
            public void OnItemClick(int position) {
                timers.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });


        //SET UP ADD TIMER BUTTON
        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTimerActivity.class);
                startActivity(intent);
            }
        });

    }

    public ArrayList<Step> getStepsFromStructure(String structure){
        ArrayList<Step> result = new ArrayList<Step>();
        JSONObject jobj;
        JSONArray steps;
        try {
             jobj = new JSONObject(structure);
             steps = jobj.getJSONArray("steps");
             for(int i=0; i< steps.length(); i++){
                 JSONObject stepFromJSON = steps.getJSONObject(i);
                 String title = stepFromJSON.get("title").toString();
                 int mins = Integer.parseInt(stepFromJSON.get("mins").toString());
                 int secs = Integer.parseInt(stepFromJSON.get("secs").toString());
                 int reps = Integer.parseInt(stepFromJSON.get("reps").toString());
                 int rest = Integer.parseInt(stepFromJSON.get("rest").toString());
                 result.add(new Step(mins, secs, title, reps, rest));
             }
        } catch (JSONException e) {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Invalid JSON :(", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return null;
        }

        return result;
    }
}