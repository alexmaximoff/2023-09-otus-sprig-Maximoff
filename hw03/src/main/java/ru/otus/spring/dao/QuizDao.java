package ru.otus.spring.dao;

import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.exceptions.QuizReadException;

import java.util.List;

public interface QuizDao {
    List<QuizQestion> getQuizData() throws QuizReadException;
}
