package ru.otus.spring.service;

import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;

public class QuizResultImpl implements QuizResult {

    private final AppConfig appConfig;

    private final IOService ioService;

    private Integer correctAnswerCount = 0;

    public QuizResultImpl(AppConfig appConfig, IOService ioService) {
        this.appConfig = appConfig;
        this.ioService = ioService;
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
        if (correctAnswerCount >= appConfig.getRightAnswersCountToPass()) {
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
