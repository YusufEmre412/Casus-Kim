package com.stark.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {


    private TextView playerNameTextView;
    private Button roleButton;
    private String playerName;
    private String playerWord;
    private ArrayList<String> playerList;
    private ArrayList<String> wordList;
    private int currentPlayerIndex;
    private boolean roleShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerList = getIntent().getStringArrayListExtra("oyuncuIsimleri");
        wordList = getIntent().getStringArrayListExtra("wordList");
        currentPlayerIndex = getIntent().getIntExtra("siradakiOyuncuIndex", 0);

        if (playerList != null && wordList != null && currentPlayerIndex < playerList.size() && currentPlayerIndex < wordList.size()) {
            playerName = playerList.get(currentPlayerIndex);
            playerWord = wordList.get(currentPlayerIndex);

            playerNameTextView = findViewById(R.id.playerNameTextView);
            roleButton = findViewById(R.id.roleButton);

            playerNameTextView.setText(playerName);

            roleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!roleShown) {
                        flipButtonAndShowRole();
                    } else {
                        showNextOrStartGame();
                    }
                }
            });

        } else {
            Toast.makeText(this, "Hata: Oyuncu bilgileri eksik veya geçersiz!", Toast.LENGTH_SHORT).show();
            finish(); // You might want to finish the activity if the data is missing
        }
    }

    private void flipButtonAndShowRole() {
        ObjectAnimator flipOut = ObjectAnimator.ofFloat(roleButton, "rotationX", 0f, 90f);
        flipOut.setDuration(300);

        flipOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                roleButton.setText(playerWord);
                roleButton.setEnabled(false);


                ObjectAnimator flipIn = ObjectAnimator.ofFloat(roleButton, "rotationX", 270f, 360f);
                flipIn.setDuration(300);
                flipIn.start();

                flipIn.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        roleButton.setRotationX(0);
                        roleButton.setEnabled(true);
                        roleShown = true;
                    }
                });

            }
        });

        flipOut.start();
    }

    private void showNextOrStartGame() {
        if (currentPlayerIndex < playerList.size() - 1) {
            goToNextPlayer();
        }
        else {
            roleButton.setText("Oyunu Başlat");

            roleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGame();
                }
            });
        }
    }

    private void goToNextPlayer() {
        Intent intent = new Intent(PlayerActivity.this, PlayerActivity.class);
        intent.putStringArrayListExtra("oyuncuIsimleri", playerList);
        intent.putStringArrayListExtra("wordList", wordList);
        intent.putExtra("siradakiOyuncuIndex", currentPlayerIndex + 1);
        startActivity(intent);
        finish();
    }


    private void startGame() {
        Intent intent = new Intent(PlayerActivity.this, GameActivity.class);
        intent.putExtra("playerCount", playerList.size());
        startActivity(intent);
        finish();
    }
}