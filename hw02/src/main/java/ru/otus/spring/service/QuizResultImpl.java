package ru.otus.spring.service;

import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;

public class QuizResultImpl implements QuizResult {

    private final IOService ioService;

    private final Integer quizPassThreshold;

    private Integer correctAnswerCount = 0;

    public QuizResultImpl(IOService ioService, Integer quizPassThreshold) {
        this.ioService = ioService;
        this.quizPassThreshold = quizPassThreshold;
    }

    @Override
    public void applyAnswer(Quiz quiz) {
        String res;
        res = ioService.readStringWithPrompt("Enter your choice:");
        int choice = (res.charAt(0) - 'A') + 1;
        if (choice == quiz.getCorrect().getAnswer()) {
            correctAnswerCount++;
            ioService.outputString("You are right.");
        } else {
            ioService.outputString("You are missed.");
        }
    }

    public void printResult(Student student) {
        if (correctAnswerCount >= quizPassThreshold) {
            ioService.outputString(
                    String.format("Congratulations, %s! You have passed, your score is %s",
                            student.getFullName(), correctAnswerCount));
        } else {
            ioService.outputString(
                    String.format("You have failed, %s, your score is %s", student.getFullName(), correctAnswerCount)
            );
        }
    }
}
