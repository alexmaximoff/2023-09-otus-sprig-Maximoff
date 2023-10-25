package ru.otus.spring.domain.quiz;

public class CorrectAnswer {
    private final int answer;

    private final String comment;

    public CorrectAnswer(int answer, String comment) {
        this.answer = answer;
        this.comment = comment;
    }

    public int getAnswer() {
        return answer;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "CorrectAnswer{" +
                "answer=" + answer +
                ", comment='" + comment + '\'' +
                '}';
    }
}
