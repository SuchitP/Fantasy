package com.example.suchitpeddireddyfinalprojectfantasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity {

    double score1;
    double score2;
    TextView score1Text;
    TextView score2Text;
    TextView winner;
    Button homeButton;
    Button teamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        score1 = TeamActivity.score1 - 40;
        score2 = TeamActivity.score2 - 40;

        score1Text = findViewById(R.id.id_score1);
        score2Text = findViewById(R.id.id_score2);
        winner = findViewById(R.id.id_winnerText);
        homeButton = findViewById(R.id.id_homeButton);
        teamButton = findViewById(R.id.id_teamButton);

        score1Text.setText("Projected:" + score1);
        score2Text.setText("Projected: " + score2);

        double dif = 0;
        if(score1 > score2){
            dif = score1 - score2;
            winner.setText("The Projected Winner is Team 1 by " + dif + " points.");
        }
        if(score1 < score2){
            dif = score2 - score1;
            winner.setText("The Projected Winner is Team 2 by " + dif + " points.");
        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, TeamActivity.class);
                startActivity(intent);
            }
        });
    }
}
