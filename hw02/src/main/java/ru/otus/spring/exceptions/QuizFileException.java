package ru.otus.spring.exceptions;

public class QuizFileException extends RuntimeException {
    public QuizFileException(String message) {
        super(message);
    }
}