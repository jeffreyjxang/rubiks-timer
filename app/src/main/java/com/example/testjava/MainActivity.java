package com.example.testjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Use milliseconds, running and wasRunning respectively
    // to record the number of milliseconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Number of milliseconds displayed
    // on the stopwatch.
    private int milliseconds = 0;

    private String bestTimeString = "";

    // Is the stopwatch running?
    private boolean running = false;

    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            milliseconds
                    = savedInstanceState
                    .getInt("milliseconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
            bestTimeString
                    = savedInstanceState
                    .getString("lastTime");
        }
        runTimer();
    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("milliseconds", milliseconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
        savedInstanceState
                .putString("lastTime", bestTimeString);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    public void onClickStart(View view)
    {
        running = true;
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    public void onClickStop(View view)
    {
        running = false;
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickReset(View view)
    {
        running = false;
        milliseconds = 0;
    }

    public void onClickStartStop(View view) {
        Button startbutton = findViewById(R.id.startandstop);
        TextView lastTimeView = findViewById(R.id.lastTime);
        if (!running) {
            running = true;
            milliseconds = 0;
            startbutton.setText("STOP");
        }
        else if (running) {
            running = false;
            int min = milliseconds / 3600;
            int secs = (milliseconds % 3600) / 60;
            int mil = milliseconds % 60;

            // Format the milliseconds into hours, minutes,
            // and milliseconds.
            bestTimeString
                    = String
                    .format(Locale.getDefault(),
                            "%d:%02d:%02d", min,
                            secs, mil);

            // Set the text view text.
            lastTimeView.setText(bestTimeString);
            startbutton.setText("START");
        }
    }

    // Sets the NUmber of milliseconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the milliseconds and
    // update the text view.
    private void runTimer()
    {

        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int min = milliseconds / 3600;
                int secs = (milliseconds % 3600) / 60;
                int mil = milliseconds % 60;

                // Format the milliseconds into hours, minutes,
                // and milliseconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", min,
                                secs, mil);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // milliseconds variable.
                if (running) {
                    milliseconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1);
            }
        });
    }
}