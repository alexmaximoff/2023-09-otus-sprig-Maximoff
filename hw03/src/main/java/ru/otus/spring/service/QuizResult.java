package ru.otus.spring.service;

import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.domain.Student;

public interface QuizResult {
    int applyAnswer(QuizQestion quizQestion);

    void printResult(Student student, int correctAnswerCount);
}
