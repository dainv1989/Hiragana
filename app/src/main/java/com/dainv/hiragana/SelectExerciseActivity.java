package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.Settings;

import java.util.ArrayList;

public class SelectExerciseActivity extends AppCompatActivity {

    private TextView tvSelectHira;
    private TextView tvSelectKata;

    private static final int QA_COUNT_SETTING_NUMBER = 4;

    private ArrayList<TextView> tvQACountSettings = new ArrayList<>(QA_COUNT_SETTING_NUMBER);

    private static int qa_number = 10;
    private final static int qa_counts[] = {5, 10, 15, 20};

    private Settings settings = null;

    private ImageView imgReading;
    private ImageView imgListening;

    private final static int READING_TEST = 1000;
    private final static int LISTENING_TEST = 2000;
    private static int exercise_type = READING_TEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/FreeMonoBold.ttf");

        tvSelectHira = (TextView)findViewById(R.id.btnSelectHira);
        tvSelectKata = (TextView)findViewById(R.id.btnSelectKata);

        tvSelectHira.setTypeface(font);
        tvSelectKata.setTypeface(font);

        tvQACountSettings.add(0, (TextView)findViewById(R.id.txtQACount5));
        tvQACountSettings.add(1, (TextView)findViewById(R.id.txtQACount10));
        tvQACountSettings.add(2, (TextView)findViewById(R.id.txtQACount15));
        tvQACountSettings.add(3, (TextView)findViewById(R.id.txtQACount20));

        imgReading = (ImageView)findViewById(R.id.imgReadingTest);
        imgListening = (ImageView)findViewById(R.id.imgSoundTest);

        /* get application settings */
        settings = new Settings(getApplicationContext());
        qa_number = settings.getQuestionCount();
        for (int i = 0; i < QA_COUNT_SETTING_NUMBER; i++) {
            if (qa_number == qa_counts[i]) {
                setSelectedQA(i);
                break;
            }
        }

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

        for(int i = 0; i < QA_COUNT_SETTING_NUMBER; i++) {
            final int index = i;
            final TextView textView = tvQACountSettings.get(i);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* update GUI */
                    setSelectedQA(index);

                    /* update to application settings */
                    if (settings != null) {
                        settings.setQuestionCount(qa_counts[index]);
                    }
                }
            });
        }
    }

    private void setQASettingsTextSizeSmall() {
        int size = tvQACountSettings.size();
        if (size < QA_COUNT_SETTING_NUMBER)
            return;

        for (int i = 0; i < QA_COUNT_SETTING_NUMBER; i++) {
            TextView txtView = tvQACountSettings.get(i);
            txtView.setSelected(false);
            txtView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(R.dimen.text_small));
        }
    }

    /**
     * Update GUI to change font size of selected question number
     * @param index position of selected question in list on GUI
     */
    private void setSelectedQA(int index) {
        int size = tvQACountSettings.size();
        if ((size < QA_COUNT_SETTING_NUMBER) || (size <= index))
            return;

        TextView textView = tvQACountSettings.get(index);

        /* convert all texts to small size */
        setQASettingsTextSizeSmall();

        /* set selected text to large size */
        textView.setSelected(true);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.text_normal));
    }
}
