 package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

 public class AddTimerActivity extends AppCompatActivity {

    FloatingActionButton timer_form_input_button;
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

                //set image for remove button
                LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);
                ImageButton remove = timer_step_view.findViewById(R.id.step_delete_image_button);
                remove.setImageResource(R.drawable.baseline_delete_24);

                //set number picker max and min vals
                NumberPicker step_mins_number_picker = (NumberPicker) timer_step_view.findViewById(R.id.step_mins_number_picker);
                step_mins_number_picker.setMinValue(0);
                step_mins_number_picker.setMaxValue(59);

                NumberPicker step_secs_number_picker = (NumberPicker) timer_step_view.findViewById(R.id.step_secs_number_picker);
                step_secs_number_picker.setMinValue(0);
                step_secs_number_picker.setMaxValue(59);

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
            NumberPicker step_mins_number_picker = child_view.findViewById(R.id.step_mins_number_picker);
            NumberPicker step_secs_number_picker = child_view.findViewById(R.id.step_secs_number_picker);
            //TODO: get reps and rest

            //instantiate step JSONObject
            JSONObject temp_step = new JSONObject();
            try{
                //get input values and add to JSONObject
                //number pickers seem buggy and return value+1 if you interact with them?
                temp_step.put("mins", step_mins_number_picker.getValue()>0 ? step_mins_number_picker.getValue()-1 : step_mins_number_picker.getValue());
                temp_step.put("secs", step_secs_number_picker.getValue()>0 ? step_secs_number_picker.getValue()-1 : step_secs_number_picker.getValue());
                temp_step.put("title", step_name_text_view.getText().toString());
                //TODO: add reps and rest

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
}