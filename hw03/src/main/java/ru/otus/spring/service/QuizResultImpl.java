package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.ResultConfig;
import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.domain.Student;

@Service
public class QuizResultImpl implements QuizResult {

    private final ResultConfig appConfig;

    private final IOService ioService;

    public QuizResultImpl(ResultConfig appConfig, IOService ioService) {
        this.appConfig = appConfig;
        this.ioService = ioService;
    }

    @Override
    public int applyAnswer(QuizQestion quizQestion) {
        //прочитать ответ как латинскую букву
        char choice = ioService.readStringWithPrompt("Enter your choice:").toUpperCase().charAt(0);
        //получим из латинской буквы номер ответа
        int choiceIndex = (choice - 'A');
        //Напечатать ответ и комментарй
        //если номер ответа совпал с номером верного ответа вернуть 1 иначе 0
        if (quizQestion.getChoiceList().get(choiceIndex).getCorrect()) {
            ioService.outputString("You are right.");
            return 1;
        } else {
            ioService.outputString("You are missed.");
            return 0;
        }
    }

    @Override
    public void printResult(Student student, int correctAnswerCount) {
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
