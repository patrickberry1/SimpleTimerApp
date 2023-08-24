 package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

 public class AddTimerActivity extends AppCompatActivity {

    Button timer_form_input_button;
    ConstraintLayout constraintLayout;
    LinearLayout.LayoutParams layoutParams;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        //INITIALIZE STUFF
        constraintLayout = (ConstraintLayout) findViewById(R.id.add_timer_constraint_layout);
        DB = new DatabaseHelper(getApplicationContext());

        //SET UP ADD TIMER BUTTON
        timer_form_input_button = findViewById(R.id.timer_form_input_button);
        timer_form_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText timer_name = (EditText) findViewById(R.id.add_timer_timer_name_edit_text);
                if (timer_name.getText().toString().length() > 0){
                    String structure = createTimerStructure();
                    boolean res = DB.insertTimer(timer_name.getText().toString(), structure);
                    Snackbar snackbar;
                    if (res){
                        snackbar = Snackbar.make(constraintLayout, "Inserted successfully!!", Snackbar.LENGTH_SHORT);
                    } else {
                        snackbar = Snackbar.make(constraintLayout, "Insert failed!!", Snackbar.LENGTH_SHORT);
                    }
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(constraintLayout, "Must enter a timer name!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        //SET UP ADD TIMER STEP BUTTON
        Button addTimerStepButton = (Button) findViewById(R.id.add_timer_step_button);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addTimerStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(getApplicationContext());
                View timer_step_view = factory.inflate(R.layout.timer_step_view, null);
                timer_step_view.setId(View.generateViewId());

                LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);
                ImageButton remove = timer_step_view.findViewById(R.id.step_delete_image_button);
                remove.setImageResource(R.drawable.baseline_delete_24);

                ll.addView(timer_step_view);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ll.removeView(timer_step_view);
                    }
                });
            }
        });
    }

    public String createTimerStructure() {

        LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);
        int child_count = ll.getChildCount();
        JSONArray steps_json_array = new JSONArray();

        for(int i=0; i<child_count; i++){
            ConstraintLayout child_view = (ConstraintLayout) ll.getChildAt(i);
            EditText step_name_text_view = child_view.findViewById(R.id.step_name_text_view);
            EditText step_mins_text_view = child_view.findViewById(R.id.step_mins_text_view);
            EditText step_secs_text_view = child_view.findViewById(R.id.step_secs_text_view);

            JSONObject temp_step = new JSONObject();
            try{
                temp_step.put("mins", Integer.parseInt(step_mins_text_view.getText().toString()));
                temp_step.put("secs", Integer.parseInt(step_secs_text_view.getText().toString()));
                steps_json_array.put(temp_step);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        JSONObject steps_json_object = new JSONObject();
        try{
            steps_json_object.put("steps", steps_json_array);
        } catch (JSONException e){
            e.printStackTrace();
        }
        String result = steps_json_object.toString();
        return result;
    }
}