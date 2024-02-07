 package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

 public class AddTimerActivity extends AppCompatActivity {

    FloatingActionButton timer_create_button;
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
        timer_create_button = findViewById(R.id.timer_create_button);
        timer_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTimer();
            }
        });

        //SET UP ADD TIMER STEP BUTTON
        FloatingActionButton addTimerStepButton = (FloatingActionButton) findViewById(R.id.add_timer_step_button);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addTimerStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate new timerstepview
                LayoutInflater factory = LayoutInflater.from(getApplicationContext());
                View timer_step_view = factory.inflate(R.layout.timer_step_view, null);
                timer_step_view.setId(View.generateViewId());
                TextView counter = timer_step_view.findViewById(R.id.timer_step_counter);
                counter.setText(timer_step_view.getId() + ".");

                //set image for remove button
                LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);
                ImageButton remove = timer_step_view.findViewById(R.id.step_delete_image_button);
                remove.setImageResource(R.drawable.baseline_delete_24);

                //set up spinners
                setUpSpinners(timer_step_view);

                //add timer step
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

        //Get timer step container layout and number of timer steps
        LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);
        int child_count = ll.getChildCount();
        //if no children break
        if (child_count == 0){
            //TODO: return no children string?
            return "";
        }

        //Instantiate JSONArray
        JSONArray steps_json_array = new JSONArray();

        //Loop through all step views
        for(int i=0; i<child_count; i++){

            //get child at index and get inputs from child
            ConstraintLayout child_view = (ConstraintLayout) ll.getChildAt(i);
            EditText step_name_text_view = child_view.findViewById(R.id.step_name_text_view);
            Spinner step_mins_spinner = child_view.findViewById(R.id.step_mins_spinner);
            Spinner step_secs_spinner = child_view.findViewById(R.id.step_secs_spinner);
            //TODO: get reps and rest
            EditText reps_input_edit_text = child_view.findViewById(R.id.reps_input_edit_text);
            EditText rest_input_edit_text = child_view.findViewById(R.id.rest_input_edit_text);

            //instantiate step JSONObject
            JSONObject temp_step = new JSONObject();
            try{
                //get input values and add to JSONObject
                //number pickers seem buggy and return value+1 if you interact with them?
                temp_step.put("mins", step_mins_spinner.getSelectedItem());
                temp_step.put("secs", step_secs_spinner.getSelectedItem());
                temp_step.put("title", step_name_text_view.getText().toString());
                temp_step.put("reps", reps_input_edit_text.getText().toString());
                temp_step.put("rest", rest_input_edit_text.getText().toString());

                //add JSONObject to JSONArray
                steps_json_array.put(temp_step);
            } catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
        //create JSONObject container to return JSONArray
        JSONObject steps_json_object = new JSONObject();
        try{
            steps_json_object.put("steps", steps_json_array);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }

        //return JSONObject as string
        String result = steps_json_object.toString();
        return result;
    }

    public void setUpSpinners(View timer_step_view) {
        Spinner step_mins_spinner = (Spinner) timer_step_view.findViewById(R.id.step_mins_spinner);
        Spinner step_secs_spinner = (Spinner) timer_step_view.findViewById(R.id.step_secs_spinner);

        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = 0; i<59; i++) {
            range.add(i);
        }

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, range);
        step_mins_spinner.setAdapter(spinnerAdapter);
        step_secs_spinner.setAdapter(spinnerAdapter);
    }

    public void CreateTimer() {
        EditText timer_name = (EditText) findViewById(R.id.add_timer_timer_name_edit_text);
        if (timer_name.getText().toString().length() > 0){
            String structure = createTimerStructure();
            if (structure == ""){
                Snackbar snackbar;
                snackbar = Snackbar.make(constraintLayout, "Invalid timer details...", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                boolean res = DB.insertTimer(timer_name.getText().toString(), structure);
                Snackbar snackbar;
                if (res){
                    snackbar = Snackbar.make(constraintLayout, "Inserted successfully!!", Snackbar.LENGTH_SHORT);
                    Intent intent = new Intent(AddTimerActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    snackbar = Snackbar.make(constraintLayout, "Insert failed!!", Snackbar.LENGTH_SHORT);
                }
                snackbar.show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Must enter a timer name!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
}