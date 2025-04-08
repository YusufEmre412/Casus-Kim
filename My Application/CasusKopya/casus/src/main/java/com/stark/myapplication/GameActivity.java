package com.stark.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.media.MediaPlayer;

public class GameActivity extends AppCompatActivity {
    private TextView gameCountdownText;
    private CountDownTimer gameCountDownTimer;
    private boolean isCountDownRunning = false;
    private MediaPlayer mediaPlayer;
    private int playerCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameCountdownText = findViewById(R.id.gameCountdownText);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);

        playerCount = getIntent().getIntExtra("playerCount", 3);

        startCountDown();

    }
    private void startCountDown() {
        if (isCountDownRunning) return;

        isCountDownRunning = true;

        int duration = (playerCount-1)*60000;
        gameCountDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            int secondsLeft = (int) (millisUntilFinished / 1000);
            gameCountdownText.setText("Kalan Süre: " + String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60));
            }

            @Override
            public void onFinish() {
            isCountDownRunning = false;
            gameCountdownText.setText("Süre Doldu!");
            playAlarmSound();
            endgame();
            }
        }.start();

    }
    private void playAlarmSound() {
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    private void endgame(){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
