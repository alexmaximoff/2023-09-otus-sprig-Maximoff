package ru.otus.spring.domain.quiz;

import java.util.Arrays;

public class QuizQestion {

    private String question;

    private String[] answerList;

    private CorrectAnswer correct;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerList(String[] answerList) {
        this.answerList = answerList;
    }

    public void setCorrect(CorrectAnswer correct) {
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswerList() {
        return answerList;
    }

    public CorrectAnswer getCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "question='" + question +
                ", answerList=" + Arrays.toString(answerList) +
                ", correct=" + correct +
                '}';
    }
}
