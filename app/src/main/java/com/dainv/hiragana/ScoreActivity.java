package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;

public class ScoreActivity extends AppCompatActivity {

    private TextView tvScore;

    private ImageView imgReplay;
    private ImageView imgGoHome;
    private ImageView imgEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();

        tvScore = (TextView)findViewById(R.id.txtTotalScore);
        imgReplay = (ImageView)findViewById(R.id.imgReplay);
        imgGoHome = (ImageView)findViewById(R.id.imgGoHome);
        imgEmotion = (ImageView)findViewById(R.id.imgScoreEmotion);

        int score = intent.getIntExtra("SCORE", 0);
        int total = intent.getIntExtra("TOTAL", 0);

        if (score >= total) {
            imgEmotion.setImageResource(R.drawable.love);
        } else if (score >= total * 0.7) {
            imgEmotion.setImageResource(R.drawable.happy);
        } else if (score >= total * 0.4) {
            imgEmotion.setImageResource(R.drawable.baby);
        } else if (score > 0) {
            imgEmotion.setImageResource(R.drawable.scared);
        } else {
            imgEmotion.setImageResource(R.drawable.crying);
        }

        tvScore.setText(score + "/" + total);

        final Context context = this;
        imgReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);
                context.startActivity(intent);
            }
        });

        imgGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }
}
