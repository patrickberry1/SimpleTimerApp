package com.example.simpletimerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class StartTimerActivity extends AppCompatActivity {

    FloatingActionButton start_timer_button;
    FloatingActionButton pause_timer_button;
    FloatingActionButton stop_timer_button;
    TextView timer_text_view;
    CountDownTimer timer;
    public int counter;
    ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_timer);

        //Bundle extras = getIntent().getExtras();
        counter = 150;

        start_timer_button = (FloatingActionButton) findViewById(R.id.start_timer_button);
        timer_text_view = (TextView) findViewById(R.id.timer_text_view);

        start_timer_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                start_timer_button.setVisibility(View.INVISIBLE);;
                start_timer_button.setClickable(false);
                pause_timer_button.setVisibility(View.VISIBLE);
                pause_timer_button.setClickable(true);
                stop_timer_button.setVisibility(View.VISIBLE);
                stop_timer_button.setClickable(true);

//                RingtoneManager ringtoneMgr = new RingtoneManager(getApplicationContext());
//                ringtoneMgr.setType(RingtoneManager.TYPE_RINGTONE);
//                Uri notification = RingtoneManager.getRingtone();
//                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);

                timer = new CountDownTimer(counter*1000, 1000){
                    public void onTick(long millisUntilFinished){
                        int mins = counter/60;
                        String minsString;
                        int secs = counter%60;
                        String secsString;

                        if (mins<10){minsString = "0" + String.valueOf(mins);} else {minsString = String.valueOf(mins);}
                        if (secs<10){secsString = "0" + String.valueOf(secs);} else {secsString = String.valueOf(secs);}
                        timer_text_view.setText(minsString + ":" + secsString);
                        counter--;
                    }
                    public  void onFinish(){
                        cl = (ConstraintLayout) findViewById(R.id.start_timer_cl);
                        Snackbar snackbar = Snackbar.make(cl, "DING DING DING!!!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        timer_text_view.setText("00:00");
                    }
                }.start();
            }
        });

        pause_timer_button = (FloatingActionButton) findViewById(R.id.pause_timer_button);
        pause_timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                timer_text_view.setText("00:00");
            }
        });
    }
}