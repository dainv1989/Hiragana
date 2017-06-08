package com.dainv.hiragana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.QuestionItem;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        tvQuestion = (TextView)findViewById(R.id.txtQuestion);
        tvAnswer1 = (TextView)findViewById(R.id.txtAnswer1);
        tvAnswer2 = (TextView)findViewById(R.id.txtAnswer2);
        tvAnswer3 = (TextView)findViewById(R.id.txtAnswer3);
        tvScore = (TextView)findViewById(R.id.txtScore);

        if (is_generated == false) {
            questions = JPChar.generateQuestions(TOTAL_QUESTION, JPChar.QTYPE_READ_HIRA);
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

    @Override
    public void onResume() {
        super.onResume();
        if (is_generated == false) {
            questions = JPChar.generateQuestions(TOTAL_QUESTION, JPChar.QTYPE_READ_HIRA);
            is_generated = true;
        }
    }

    private void showQuestion(QuestionItem question) {
        tvQuestion.setText(question.getQuestion());
        tvAnswer1.setText(question.getAnswers().get(0));
        tvAnswer2.setText(question.getAnswers().get(1));
        tvAnswer3.setText(question.getAnswers().get(2));
        tvScore.setText(score + "/" + questions.size());
    }

    private void checkAnswer(String answer) {
        if (answer == questions.get(question_index).getQuestion())
            score++;

        if (question_index == (questions.size() - 1)) {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questions.size());
            startActivity(intent);

            is_generated = false;
        } else {
            question_index++;
            showQuestion(questions.get(question_index));
        }
    }
}
