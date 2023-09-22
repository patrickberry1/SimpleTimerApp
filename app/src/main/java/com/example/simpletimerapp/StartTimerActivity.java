package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StartTimerActivity extends AppCompatActivity {

    //Views
    FloatingActionButton start_timer_button;
    FloatingActionButton pause_timer_button;
    FloatingActionButton stop_timer_button;
    TextView timer_text_view;
    TextView step_title_text_view;
    ConstraintLayout cl;
    ProgressBar progress_bar;
    ListView timer_step_list_view;


    //Timer variables
    CountDownTimer timer;
    List<Step> steps;
    String name;
    Step currStep;
    int totalSeconds;
    int stepIndex;
    int stepSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_timer);

        //Get list of timers and timer name from extras
        Bundle extras = getIntent().getExtras();
        steps = (List<Step>) extras.getSerializable("Steps");
        name = extras.getString("Name");
        setTitle(name);

        //Setup tone generator
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        //Set vars
        stepIndex = 0;
        currStep = steps.get(stepIndex);
        stepSeconds=(currStep.minutes*60) + currStep.seconds;
        totalSeconds = 0;
        for(int i=0; i<steps.size(); i++){
            totalSeconds+=steps.get(i).seconds;
            totalSeconds+=steps.get(i).minutes*60;
            totalSeconds++;
        }

        //Views for timer display
        start_timer_button = (FloatingActionButton) findViewById(R.id.start_timer_button);
        timer_text_view = (TextView) findViewById(R.id.timer_text_view);
        step_title_text_view = (TextView) findViewById(R.id.step_title_text_view);
        step_title_text_view.setText(currStep.title);
        progress_bar = (ProgressBar) findViewById(R.id.timer_progress_bar);
        progress_bar.setMax(totalSeconds);

        //Set view stuff
        //Get minutes and seconds to display
        String tempMins;
        String tempSecs;
        int mins = currStep.minutes;
        int secs = currStep.seconds;

        if (mins < 10) {
            tempMins = String.format("0%d", mins);
        } else {
            tempMins = String.valueOf(mins);
        }
        if (secs < 10) {
            tempSecs = String.format("0%d", secs);
        } else {
            tempSecs = String.valueOf(secs);
        }

        //Display mins and secs
        timer_text_view.setText(tempMins + ":" + tempSecs);


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

                setUpNextText();

                timer = new CountDownTimer(totalSeconds * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(stepSeconds<0){
                            stepIndex++;
                            currStep=steps.get(stepIndex);
                            if (currStep!=null){
                                stepSeconds=(currStep.minutes*60) + currStep.seconds;
                                step_title_text_view.setText(currStep.title);
                            }
                        }

                        if (stepSeconds <= 3 && stepSeconds > 0) {
                            toneGen1.startTone(ToneGenerator.TONE_SUP_RINGTONE, 400);
                        } else if (stepSeconds == 30) {
                            toneGen1.startTone(ToneGenerator.TONE_SUP_RINGTONE, 200);
                        } else if (stepSeconds == 10) {
                            toneGen1.startTone(ToneGenerator.TONE_SUP_RINGTONE, 200);
                        } else if (stepSeconds == 0) {
                            toneGen1.startTone(ToneGenerator.TONE_SUP_RINGTONE, 800);
                        }

                        //set up next text
                        setUpNextText();

                        //Get minutes and seconds to display
                        String minsString;
                        String secsString;
                        int mins = stepSeconds / 60;
                        int secs = stepSeconds % 60;

                        if (mins < 10) {
                            minsString = String.format("0%d", mins);
                        } else {
                            minsString = String.valueOf(mins);
                        }
                        if (secs < 10) {
                            secsString = String.format("0%d", secs);
                        } else {
                            secsString = String.valueOf(secs);
                        }

                        //Display mins and secs
                        timer_text_view.setText(minsString + ":" + secsString);

                        //tick up progress bar
                        progress_bar.incrementProgressBy(1);
                        //Tick down totalSeconds
                        stepSeconds--;
                    }

                    public void onFinish() {
                        cl = (ConstraintLayout) findViewById(R.id.start_timer_cl);
                        Snackbar snackbar = Snackbar.make(cl, "DING DING DING!!!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        timer_text_view.setText("00:00");
                        start_timer_button.setVisibility(View.VISIBLE);
                        start_timer_button.setClickable(true);
                        pause_timer_button.setVisibility(View.INVISIBLE);
                        pause_timer_button.setClickable(false);
                        stop_timer_button.setVisibility(View.INVISIBLE);
                        stop_timer_button.setClickable(false);

                        //Set vars
                        stepIndex = 0;
                        currStep = steps.get(stepIndex);
                        stepSeconds=(currStep.minutes*60) + currStep.seconds;
                        totalSeconds = 0;
                        step_title_text_view.setText(currStep.title);
                        for(int i=0; i<steps.size(); i++){
                            totalSeconds+=steps.get(i).seconds;
                            totalSeconds+=steps.get(i).minutes*60;
                            totalSeconds++;
                        }
                        progress_bar.setProgress(0);
                    }
                }.start();
            }
        });

        //set up pause button
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

        //set up stop button
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

                //Set vars
                stepIndex = 0;
                currStep = steps.get(stepIndex);
                stepSeconds=(currStep.minutes*60) + currStep.seconds;
                totalSeconds = 0;
                for(int i=0; i<steps.size(); i++){
                    totalSeconds+=steps.get(i).seconds;
                    totalSeconds+=steps.get(i).minutes*60;
                    totalSeconds++;
                }
                timer_text_view.setText("00:00");
                progress_bar.setProgress(0);
            }
        });
    }

    public void setUpNextText() {
        TextView up_next_text = (TextView) findViewById(R.id.up_next_text_view);
        Step next_step;
        String next_text;

        if (stepIndex < steps.size()-1){
            next_step = steps.get(stepIndex+1);
            next_text = "Up Next: " + next_step.title;
        } else {
            next_text = "Up Next: Finished!!";
        }

        up_next_text.setText(next_text);
    }
}