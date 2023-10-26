package ru.otus.spring.domain.quiz;

import java.util.List;

public class QuizQestion {

    //текст вопроса
    private final String question;

    //коллекция вариантов ответов с признаком правильный и комментарием
    private final List<QuestionChoice> choiceList;

    public QuizQestion(String question, List<QuestionChoice> choiceList) {
        this.question = question;
        this.choiceList = choiceList;
    }

    public String getQuestion() {
        return question;
    }

    public List<QuestionChoice> getChoiceList() {
        return choiceList;
    }
}
