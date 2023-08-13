 package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

 public class AddTimerActivity extends AppCompatActivity {

    Button timer_form_input_button;
    ConstraintLayout constraintLayout;
    LinearLayout.LayoutParams layoutParams;
    int steps;
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        timer_form_input_button = findViewById(R.id.timer_form_input_button);
        timer_form_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(constraintLayout, "Added Timer (not really)", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });


        Button addTimerStepButton = (Button) findViewById(R.id.add_timer_step_button);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        addTimerStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout ll = (LinearLayout) findViewById(R.id.timer_step_container_ll);

                LinearLayout hll = new LinearLayout(getApplicationContext());
                hll.setOrientation(LinearLayout.HORIZONTAL);
                hll.setGravity(Gravity.CENTER_HORIZONTAL);
                hll.setId(View.generateViewId());

                EditText stepName = new EditText(getApplicationContext());
                stepName.setInputType(InputType.TYPE_CLASS_TEXT);
                stepName.setText("Step " + ++steps);
                stepName.setHint("Step Name");
                stepName.setId(View.generateViewId());

                EditText mins = new EditText(getApplicationContext());
                mins.setInputType(InputType.TYPE_CLASS_NUMBER);
                mins.setHint("minutes");
                mins.setId(View.generateViewId());

                EditText secs = new EditText(getApplicationContext());
                secs.setInputType(InputType.TYPE_CLASS_NUMBER);
                secs.setHint("seconds");
                secs.setId(View.generateViewId());

                ImageButton remove = new ImageButton(getApplicationContext());
                remove.setImageResource(R.drawable.baseline_delete_24);
                remove.setId(View.generateViewId());
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hll.removeAllViews();
                        ll.removeView(hll);
                    }
                });

                ll.addView(hll);
                hll.addView(stepName);
                hll.addView(mins);
                hll.addView(secs);
                hll.addView(remove);
                System.out.println(hll.getId());
                System.out.println(stepName.getId());
                System.out.println(mins.getId());
                System.out.println(secs.getId());
                System.out.println(remove.getId());
            }
        });


    }
}