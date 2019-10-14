package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Boolean flag = false;
    int timeRemaining = 0;
    CountDownTimer countDownTimer;
    TextView textView;
    MediaPlayer mediaPlayer;

    public void updateTime(int time) {
        textView = findViewById(R.id.textView);
        timeRemaining = time;
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining - minutes*60;
        String mins = String.valueOf(minutes);
        String secs = String.valueOf(seconds);
        if(minutes<10) {
            mins = "0"+mins;
        }
        if(seconds<10) {
            secs = "0"+secs;
        }
        String timeString = mins+" : "+secs;
        textView.setText(timeString);
    }

    public void PausePlay(View view) {
        Button button = findViewById(R.id.button);
        SeekBar seekBar = findViewById(R.id.seekBar);
        if (flag) {
            flag = !flag;
            button.setText("Play");
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            mediaPlayer.pause();
        } else {
            button.setText("Pause");
            flag = !flag;
            dispTimer(timeRemaining);
            seekBar.setEnabled(false);
            countDownTimer.start();
        }
    }

    public void displayTime(long time) {
        int totalSecs = (int) time / 1000;
        int mins = totalSecs / 60;
        int secs = totalSecs - mins * 60;
        String minutes, seconds;
        if (mins < 10) {
            minutes = "0" + mins;
        } else {
            minutes = String.valueOf(mins);
        }
        if (secs < 10) {
            seconds = "0" + secs;
        } else {
            seconds = String.valueOf(secs);
        }
        String timeLeft = minutes + " : " + seconds;
        textView.setText(timeLeft);
        timeRemaining = totalSecs;
    }

    public void playSound() {
        mediaPlayer = MediaPlayer.create(this,R.raw.sound);
        mediaPlayer.start();
        Button button = findViewById(R.id.button);
        button.setText("Stop");
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setEnabled(true);
    }

    public void dispTimer(int time) {
        countDownTimer = new CountDownTimer(1000 * time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                displayTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                playSound();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(3599);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}