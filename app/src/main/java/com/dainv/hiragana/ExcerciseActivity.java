package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.QuestionItem;
import com.dainv.hiragana.model.Settings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.List;

public class ExcerciseActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvAnswer1;
    private TextView tvAnswer2;
    private TextView tvAnswer3;

    private TextView tvScore;

    private ImageView imgSpeaker;
    private ImageView imgSound;

    private AdView adView;

    private static Settings settings = null;

    private static List<QuestionItem> questions;
    private static int question_index = 0;
    private static int question_type = JPChar.QTYPE_READ_HIRA;

    /* decide whether generate new set of questions or not */
    private static boolean is_generated = false;

    private static int score = 0;

    private static final int TOTAL_QUESTION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        final Context context = this;

        tvQuestion      = (TextView)findViewById(R.id.txtQuestion);
        tvAnswer1       = (TextView)findViewById(R.id.txtAnswer1);
        tvAnswer2       = (TextView)findViewById(R.id.txtAnswer2);
        tvAnswer3       = (TextView)findViewById(R.id.txtAnswer3);
        tvScore         = (TextView)findViewById(R.id.txtCorrectCount);

        imgSpeaker      = (ImageView)findViewById(R.id.imgSpeaker);
        imgSound        = (ImageView)findViewById(R.id.imgBigSpeaker);
        imgSound.setVisibility(View.INVISIBLE);

        adView = (AdView)findViewById(R.id.adsExerBanner);
        adView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1F17B575D2A0B81A953E526D33694A52")
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
            }
        });
        adView.loadAd(adRequest);

        settings = new Settings(getApplicationContext());
        if(settings.getExerciseSoundConfig()) {
            imgSpeaker.setImageResource(R.mipmap.speaker_enable);
        } else {
            imgSpeaker.setImageResource(R.mipmap.speaker_disable);
        }

        question_type = getIntent().getIntExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);

        if (is_generated == false) {
            int quest_count = settings.getQuestionCount();

            /* if there is something wrong, set question count to default value */
            if (quest_count <= 0)
                quest_count = TOTAL_QUESTION;

            questions = JPChar.generateQuestions(quest_count, question_type);
            is_generated = true;
        }

        showQuestion(questions.get(question_index));

        if ((question_type == JPChar.QTYPE_SOUND_HIRA) ||
            (question_type == JPChar.QTYPE_SOUND_KATA)) {
            imgSound.setVisibility(View.VISIBLE);
            tvQuestion.setVisibility(View.INVISIBLE);
        }

        imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JPChar.playSound(tvQuestion.getText().toString(), context);
            }
        });

        tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer1.getText().toString());
            }
        });

        tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer2.getText().toString());
            }
        });

        tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer3.getText().toString());
            }
        });

        imgSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings == null)
                    return;

                if(settings.getExerciseSoundConfig()) {
                    imgSpeaker.setImageResource(R.mipmap.speaker_disable);
                    settings.setExerciseSoundConfig(false);
                } else {
                    imgSpeaker.setImageResource(R.mipmap.speaker_enable);
                    settings.setExerciseSoundConfig(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        is_generated = false;
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        if (adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null)
            adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adView != null)
            adView.destroy();
        super.onDestroy();
    }

    private void showQuestion(QuestionItem question) {
        tvQuestion.setText(question.getQuestion());
        tvAnswer1.setText(question.getAnswers().get(0));
        tvAnswer2.setText(question.getAnswers().get(1));
        tvAnswer3.setText(question.getAnswers().get(2));

        /* show score information */
        tvScore.setText(score + "");

        /* play sound if question is listening test */
        if ((question_type == JPChar.QTYPE_SOUND_HIRA) ||
            (question_type == JPChar.QTYPE_SOUND_KATA)) {
            JPChar.playSound(tvQuestion.getText().toString(), this);
        }
    }

    private void checkAnswer(String answer) {
        QuestionItem question = questions.get(question_index);
        boolean is_correct =question.isCorrect(answer);

        if (is_correct)
            score++;
        playSound(is_correct);

        if (question_index == (questions.size() - 1)) {
            /* show score screen */
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            intent.putExtra("QUESTION_TYPE", question_type);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questions.size());

            startActivity(intent);

            /* reset information */
            is_generated = false;
            question_index = 0;
            score = 0;
        } else {
            /* show next question in question list */
            question_index++;
            showQuestion(questions.get(question_index));
        }
    }

    private static final String correctSound = "audio/ding.mp3";
    private static final String wrongSound = "audio/ohoh.mp3";

    private void playSound(boolean correct) {
        /* do not play sound if setting sound is off */
        if ((settings != null) && (settings.getExerciseSoundConfig() == false))
            return;

        AssetManager assetManager = this.getAssets();
        final MediaPlayer player = new MediaPlayer();
        String audioFile = correctSound;
        if (!correct)
            audioFile = wrongSound;

        try {
            AssetFileDescriptor fd = assetManager.openFd(audioFile);
            player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            player.prepare();
            player.start();

            /* release resource after playing audio */
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(this.getClass().getSimpleName(), "open sound file failed");
        }
    }
}
