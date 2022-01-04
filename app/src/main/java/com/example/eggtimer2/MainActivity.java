package com.example.eggtimer2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar seekBar;
    boolean countIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        goButton = findViewById(R.id.startButton);
        timerTextView = findViewById(R.id.countDownTextView);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void startTimer(View view){
        if (countIsActive){
           resetTimer();
        }
        else {
            countIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP!");
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000 + 100,1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int)l/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.sound);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }


    public void updateTimer(int secLeft){
        int min = secLeft /60;
        int sec =  secLeft - (min * 60);
        String secStr = Integer.toString(sec);
        if (sec <= 9 && sec >=0){
            secStr = "0" + sec;
        }
        timerTextView.setText(min + ":" + secStr);
    }

    public void resetTimer(){
        timerTextView.setText("0:30");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        countDownTimer.cancel();
        goButton.setText("GO!");
        countIsActive = false;
    }
}
