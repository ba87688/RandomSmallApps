package com.evan.stopwatchapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button start, stop, reset;


    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the the values were saved when there was a configuration change, restore them...
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

            Log.d("true or false", "not running" + running);
            Log.d("sec", "not running" + seconds);


            if (running) {
                startTimer();
            }
            Log.d("destroyed", "not null act");

        }

        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        reset = (Button) findViewById(R.id.resetButton);

//        timer = (TextView) findViewById(R.id.timer);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                    startTimer();

                }

            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

    }

    //activity not visible anymore. could get destroyed or restarted.
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            running = wasRunning;
        }
    }


    //called after on stop be4 destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("destroyed", "destroyed act");
        //save values
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);

    }


    private void startTimer() {
        running = true;
         final TextView timer = (TextView) findViewById(R.id.timer);


        final Handler handler = new Handler();
        //use post method to avoid delay
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                //hours  minutes and seconds format
                String time = String.format(Locale.getDefault(), "%d:%2d:%02d", hours, minutes, secs);

                timer.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    private void stopTimer() {
        running = false;


    }

    private void resetTimer() {
        running = false;
        seconds = 0;


    }


}
