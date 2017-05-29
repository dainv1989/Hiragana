package com.dainv.hiragana;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.QuestionItem;

import java.util.List;

public class ExcerciseActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvAnswer1;
    private TextView tvAnswer2;
    private TextView tvAnswer3;

    private static List<QuestionItem> questions;
    private static int question_index = 0;
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

        if (is_generated == false) {
            questions = JPChar.generateQuestions(TOTAL_QUESTION, JPChar.QTYPE_READ_HIRA);
            is_generated = true;
        }

        tvQuestion.setText(questions.get(question_index).getQuestion());
        tvAnswer1.setText(questions.get(question_index).getAnswers().get(0));
        tvAnswer2.setText(questions.get(question_index).getAnswers().get(1));
        tvAnswer3.setText(questions.get(question_index).getAnswers().get(2));
    }
}
