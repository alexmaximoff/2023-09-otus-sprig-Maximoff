package ru.otus.spring.dao;

import ru.otus.spring.domain.Quiz;

import java.util.List;

public interface QuizDao {
    List<Quiz> getQuizData();
}
