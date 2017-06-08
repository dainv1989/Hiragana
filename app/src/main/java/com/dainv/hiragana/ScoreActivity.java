package com.dainv.hiragana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();

        tvScore = (TextView)findViewById(R.id.txtTotalScore);
        int score = intent.getIntExtra("SCORE", 0);
        int total = intent.getIntExtra("TOTAL", 0);

        tvScore.setText(score + "/" + total);
    }
}
