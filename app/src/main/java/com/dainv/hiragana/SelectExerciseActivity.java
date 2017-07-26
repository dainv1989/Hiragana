package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;

public class SelectExerciseActivity extends AppCompatActivity {

    private TextView tvSelectHira;
    private TextView tvSelectKata;

    private ImageView imgReading;
    private ImageView imgListening;

    private final static int READING_TEST = 1000;
    private final static int LISTENING_TEST = 2000;
    private static int exercise_type = READING_TEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        tvSelectHira = (TextView)findViewById(R.id.btnSelectHira);
        tvSelectKata = (TextView)findViewById(R.id.btnSelectKata);

        imgReading = (ImageView)findViewById(R.id.imgReadingTest);
        imgListening = (ImageView)findViewById(R.id.imgSoundTest);

        /* init selected state */
        exercise_type = READING_TEST;
        imgReading.setSelected(true);
        imgListening.setSelected(false);

        imgReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise_type = READING_TEST;
                imgReading.setSelected(true);
                imgListening.setSelected(false);
            }
        });

        imgListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise_type = LISTENING_TEST;
                imgListening.setSelected(true);
                imgReading.setSelected(false);
            }
        });

        final Context context = this;
        tvSelectHira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                if (exercise_type == LISTENING_TEST)
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_SOUND_HIRA);
                else
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);

                startActivity(intent);
            }
        });

        tvSelectKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                if (exercise_type == LISTENING_TEST)
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_SOUND_KATA);
                else
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_READ_KATA);
                startActivity(intent);
            }
        });
    }
}
