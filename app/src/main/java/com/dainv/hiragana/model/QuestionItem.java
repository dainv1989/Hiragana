package com.dainv.hiragana.model;

import java.util.List;

/**
 * Created by Dai Nguyen on 29/05/2017.
 */

public class QuestionItem {
    private String question;
    private String correctAnswer;
    private List<String> possibleAnswers;

    public boolean isCorrect(String value) {
        return (value == correctAnswer);
    }

    public void setCorrectAnswer(String value) {
        this.correctAnswer = value;
    }

    public void setAnswers(List<String> values) {
        this.possibleAnswers = values;
    }

    public List<String> getAnswers() {
        return this.possibleAnswers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
