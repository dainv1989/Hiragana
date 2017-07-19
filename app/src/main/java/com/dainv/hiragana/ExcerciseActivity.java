package com.dainv.hiragana;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.QuestionItem;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class ExcerciseActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvAnswer1;
    private TextView tvAnswer2;
    private TextView tvAnswer3;

    private TextView tvScore;

    private static List<QuestionItem> questions;
    private static int question_index = 0;
    private static int score = 0;
    private static final int TOTAL_QUESTION = 10;
    private static boolean is_generated = false;
    private static int question_type = JPChar.QTYPE_READ_HIRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        tvQuestion      = (TextView)findViewById(R.id.txtQuestion);
        tvAnswer1       = (TextView)findViewById(R.id.txtAnswer1);
        tvAnswer2       = (TextView)findViewById(R.id.txtAnswer2);
        tvAnswer3       = (TextView)findViewById(R.id.txtAnswer3);

        tvScore         = (TextView)findViewById(R.id.txtCorrectCount);

        question_type = getIntent().getIntExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);

        if (is_generated == false) {
            questions = JPChar.generateQuestions(TOTAL_QUESTION, question_type);
            is_generated = true;
        }

        showQuestion(questions.get(question_index));

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
    }

    private void showQuestion(QuestionItem question) {
        tvQuestion.setText(question.getQuestion());
        tvAnswer1.setText(question.getAnswers().get(0));
        tvAnswer2.setText(question.getAnswers().get(1));
        tvAnswer3.setText(question.getAnswers().get(2));

        /* show score information */
        tvScore.setText(score + "");
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
