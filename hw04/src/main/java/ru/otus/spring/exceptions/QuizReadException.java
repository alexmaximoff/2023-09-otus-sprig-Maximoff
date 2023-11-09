package ru.otus.spring.exceptions;

public class QuizReadException extends RuntimeException {
    public QuizReadException(String message, Exception cause) {
        super(message, cause);
    }
}