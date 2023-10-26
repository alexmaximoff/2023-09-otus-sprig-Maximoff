package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.quiz.QuestionChoice;
import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.domain.Student;
import ru.otus.spring.exceptions.QuizReadException;
import ru.otus.spring.message.LocalMsgService;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    private final IOService ioService;

    private final StudentService studentService;

    private final LocalMsgService localMsgService;

    private final QuizResult quizResult;

    public QuizServiceImpl(QuizDao quizDao, IOService ioService, StudentService studentService,
                           LocalMsgService localMsgService, QuizResult quizResult) {
        this.quizDao = quizDao;
        this.ioService = ioService;
        this.studentService = studentService;
        this.localMsgService = localMsgService;
        this.quizResult = quizResult;
    }

    @Override
    public void runQuiz() {
        List<QuizQestion> quizQestionList;
        try {
            quizQestionList = quizDao.getQuizData();
        } catch (QuizReadException e) {
            ioService.outputString("Caught : " + e);
            ioService.outputString("Actual cause: " + e.getCause());
            return;
        }
        ioService.outputString(localMsgService.getMsgByCode("QuizName"));
        //состояние квиза хранится только на время прохождения
        Student student = studentService.greatStudent();
        int correctAnswerCount = 0;
        int i = 1;
        for (QuizQestion quizQestion : quizQestionList) {
            //напечатать вопрос
            ioService.outputString(formatQuestion(quizQestion, i++));
            correctAnswerCount += quizResult.applyAnswer(quizQestion);
            ioService.outputString(formatQuestionTip(quizQestion));
        }
        //вывести результат для студента
        quizResult.printResult(student, correctAnswerCount);
    }

    private String formatChoice(String choice, int idx) {
        return Character.toString('A' + idx) + "." + choice;
    }

    private String formatQuestion(QuizQestion quizQestion, int idx) {
        final String eol = System.lineSeparator();
        StringBuilder questionSB = new StringBuilder();
        questionSB.append(idx).append(".").append(quizQestion.getQuestion()).append(eol);
        questionSB.append("Choose the correct:").append(eol);
        int choiceIdx = 0;
        for (QuestionChoice choice : quizQestion.getChoiceList()) {
            questionSB.append("\t").append(formatChoice(choice.getChoiceText(), choiceIdx++)).append(eol);
        }

        return questionSB.toString();
    }

    private String formatQuestionTip(QuizQestion quizQestion) {
        final String eol = System.lineSeparator();
        StringBuilder questionSB = new StringBuilder();
        questionSB.append("Tip: ");
        for (QuestionChoice choice : quizQestion.getChoiceList()) {
            if (choice.getCorrect()) {
                questionSB.append(choice.getComment()).append(eol);
            }
        }
        questionSB.append("Correct choice is: ");
        int correctIdx = 0;
        for (QuestionChoice choice : quizQestion.getChoiceList()) {
            if (choice.getCorrect()) {
                questionSB.append(formatChoice(choice.getChoiceText(), correctIdx)).append(" ");
            }
            correctIdx++;
        }
        return questionSB.toString().trim();
    }
}