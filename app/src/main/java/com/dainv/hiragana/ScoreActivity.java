package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;

public class ScoreActivity extends AppCompatActivity {

    private TextView tvScore;

    private ImageView imgReplay;
    private ImageView imgGoHome;
    private ImageView imgEmotion;

    private static int question_type = JPChar.QTYPE_READ_HIRA;
    private static int mark = 0;
    private static int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvScore = (TextView)findViewById(R.id.txtTotalScore);
        imgReplay = (ImageView)findViewById(R.id.imgReplay);
        imgGoHome = (ImageView)findViewById(R.id.imgGoHome);
        imgEmotion = (ImageView)findViewById(R.id.imgScoreEmotion);

        Intent intent = getIntent();
        mark = intent.getIntExtra("SCORE", 0);
        total = intent.getIntExtra("TOTAL", 0);
        question_type = intent.getIntExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);

        showEmotion(mark);
        tvScore.setText(mark + "/" + total);

        final Context context = this;
        imgReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("QUESTION_TYPE", question_type);
                context.startActivity(intent);
            }
        });

        imgGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void showEmotion(int score) {
        /* set emotion face based on score */
        if (score == total) {
            imgEmotion.setImageResource(R.drawable.love);
        } else if ((score > total * 0.8) && (score < total)) {
            imgEmotion.setImageResource(R.drawable.smile);
        } else if ((score > total * 0.6) && (score <= total * 0.8)) {
            imgEmotion.setImageResource(R.drawable.happy);
        } else if ((score > total * 0.4) && (score <= total * 0.6)) {
            imgEmotion.setImageResource(R.drawable.sceptic);
        } else if ((score > total * 0.2) && (score <= total * 0.4)) {
            imgEmotion.setImageResource(R.drawable.confused);
        } else if ((score > 0) && (score <= total * 0.2)) {
            imgEmotion.setImageResource(R.drawable.sad);
        } else {
            imgEmotion.setImageResource(R.drawable.tongue_out);
        }
    }
}
