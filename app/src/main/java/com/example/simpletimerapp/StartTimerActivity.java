package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class StartTimerActivity extends AppCompatActivity {

    //Views
    FloatingActionButton start_timer_button;
    FloatingActionButton pause_timer_button;
    FloatingActionButton stop_timer_button;
    TextView timer_text_view;
    TextView rep_counter_text_view;
    ConstraintLayout cl;

    //Timer variables
    CountDownTimer timer;
    public int totalSeconds;
    public int stepIndex;
    public int repIndex;
    List<Step> steps;
    String name;
    Step currStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_timer);

        //Get list of timers and timer name from extras
        Bundle extras = getIntent().getExtras();
        steps = (List<Step>) extras.getSerializable("Steps");
        name = extras.getString("Name");

        //Set vars
        stepIndex = 0;
        repIndex = 1;
        currStep = steps.get(stepIndex);

        //Views for timer display
        start_timer_button = (FloatingActionButton) findViewById(R.id.start_timer_button);
        timer_text_view = (TextView) findViewById(R.id.timer_text_view);
        rep_counter_text_view = (TextView) findViewById(R.id.rep_counter_text_view);
        rep_counter_text_view.setText(String.format("Rep %d", repIndex));

        //Start timer
        start_timer_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                //Display pause and stop buttons
                start_timer_button.setVisibility(View.INVISIBLE);
                start_timer_button.setClickable(false);
                pause_timer_button.setVisibility(View.VISIBLE);
                pause_timer_button.setClickable(true);
                stop_timer_button.setVisibility(View.VISIBLE);
                stop_timer_button.setClickable(true);

                //Set totalSeconds value from current step
                totalSeconds = (currStep.minutes * 60) + currStep.seconds;

                timer = new CountDownTimer(totalSeconds * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        //Get minutes and seconds to display
                        int mins = totalSeconds / 60;
                        String minsString;
                        int secs = totalSeconds % 60;
                        String secsString;

                        if (mins < 10) {
                            minsString = "0" + String.valueOf(mins);
                        } else {
                            minsString = String.valueOf(mins);
                        }
                        if (secs < 10) {
                            secsString = "0" + String.valueOf(secs);
                        } else {
                            secsString = String.valueOf(secs);
                        }

                        //Display mins and secs
                        timer_text_view.setText(minsString + ":" + secsString);

                        //Tick down totalSeconds
                        totalSeconds--;
                    }

                    public void onFinish() {
                        cl = (ConstraintLayout) findViewById(R.id.start_timer_cl);
                        Snackbar snackbar = Snackbar.make(cl, "DING DING DING!!!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        if (repIndex < currStep.reps){
                            repIndex++;
                            rep_counter_text_view.setText(String.format("Rep %d", repIndex ));
                            totalSeconds = (currStep.minutes * 60) + currStep.seconds;
                            timer.start();
                        } else {
                            timer_text_view.setText("00:00");
                            start_timer_button.setVisibility(View.VISIBLE);
                            start_timer_button.setClickable(true);
                            pause_timer_button.setVisibility(View.INVISIBLE);
                            pause_timer_button.setClickable(false);
                            stop_timer_button.setVisibility(View.INVISIBLE);
                            stop_timer_button.setClickable(false);
                        }
                    }
                }.start();
            }
        });

        pause_timer_button = (FloatingActionButton) findViewById(R.id.pause_timer_button);
        pause_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_timer_button.setVisibility(View.VISIBLE);
                start_timer_button.setClickable(true);
                pause_timer_button.setVisibility(View.INVISIBLE);
                pause_timer_button.setClickable(false);
                stop_timer_button.setVisibility(View.INVISIBLE);
                stop_timer_button.setClickable(false);
                timer.cancel();
            }
        });

        stop_timer_button = (FloatingActionButton) findViewById(R.id.stop_timer_button);
        stop_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_timer_button.setVisibility(View.VISIBLE);
                start_timer_button.setClickable(true);
                pause_timer_button.setVisibility(View.INVISIBLE);
                pause_timer_button.setClickable(false);
                stop_timer_button.setVisibility(View.INVISIBLE);
                stop_timer_button.setClickable(false);
                timer.cancel();
                totalSeconds = 0;
                stepIndex = 0;
                timer_text_view.setText("00:00");
            }
        });
    }
}