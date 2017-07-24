package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;

public class SelectExerciseActivity extends AppCompatActivity {

    private TextView tvSelectHira;
    private TextView tvSelectKata;

    private Switch swExerSound;

    private ImageView imgSoundExerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        tvSelectHira = (TextView)findViewById(R.id.btnSelectHira);
        tvSelectKata = (TextView)findViewById(R.id.btnSelectKata);

        swExerSound = (Switch)findViewById(R.id.swcExerSound);
        swExerSound.setChecked(false);

        imgSoundExerInfo = (ImageView)findViewById(R.id.imgExerSoundInfo);
        imgSoundExerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: display help
            }
        });

        final Context context = this;
        tvSelectHira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                if (swExerSound.isChecked())
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

                if (swExerSound.isChecked())
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_SOUND_KATA);
                else
                    intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_READ_KATA);
                startActivity(intent);
            }
        });
    }
}
