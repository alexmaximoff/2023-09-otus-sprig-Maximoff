package ru.otus.spring.service;

import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;

public interface QuizResult {
    void applyAnswer(Quiz quiz);

    void printResult(Student student);
}
