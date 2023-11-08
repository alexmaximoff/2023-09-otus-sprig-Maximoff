package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.ResultConfig;
import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.domain.Student;
import ru.otus.spring.message.LocalMsgService;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    private final ResultConfig appConfig;

    private final IOService ioService;

    private final LocalMsgService localMsgService;

    public QuizResultServiceImpl(ResultConfig appConfig, IOService ioService, LocalMsgService localMsgService) {
        this.appConfig = appConfig;
        this.ioService = ioService;
        this.localMsgService = localMsgService;
    }

    @Override
    public int applyAnswer(QuizQestion quizQestion) {
        //прочитать ответ как латинскую букву
        char choice = ioService.readStringWithPrompt(
                localMsgService.getMsgByCode("QuizResSrvEnter")
        ).toUpperCase().charAt(0);
        //получим из латинской буквы номер ответа
        int choiceIndex = (choice - 'A');
        //Напечатать ответ и комментарй
        //если номер ответа совпал с номером верного ответа вернуть 1 иначе 0
        if (quizQestion.getChoiceList().get(choiceIndex).getCorrect()) {
            ioService.outputString(localMsgService.getMsgByCode("QuizResSrvRight"));
            return 1;
        } else {
            ioService.outputString(localMsgService.getMsgByCode("QuizResSrvMissed"));
            return 0;
        }
    }

    @Override
    public void printResult(Student student, int correctAnswerCount) {
        if (correctAnswerCount >= appConfig.getRightAnswersCountToPass()) {
            ioService.outputString(
                    String.format(localMsgService.getMsgByCode("QuizResSrvPassed"),
                            student.getFullName(), correctAnswerCount));
        } else {
            ioService.outputString(
                    String.format(localMsgService.getMsgByCode("QuizResSrvFailed"),
                            student.getFullName(), correctAnswerCount)
            );
        }
    }
}
