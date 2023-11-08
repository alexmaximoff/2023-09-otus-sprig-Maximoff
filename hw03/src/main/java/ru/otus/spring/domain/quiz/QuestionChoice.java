package ru.otus.spring.domain.quiz;

public class QuestionChoice {
    private final String choiceText;

    private final String comment;

    private final boolean isCorrect;

    public QuestionChoice(String choiceText, String comment, Boolean isCorrect) {
        this.choiceText = choiceText;
        this.comment = comment;
        this.isCorrect = isCorrect;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public String getComment() {
        return comment;
    }

    public boolean getCorrect() {
        return isCorrect;
    }
}
