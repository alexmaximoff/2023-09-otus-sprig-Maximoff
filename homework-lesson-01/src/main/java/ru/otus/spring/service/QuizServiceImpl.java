package ru.otus.spring.service;

import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.exceptions.QuizFileException;

import java.util.List;

public class QuizServiceImpl implements QuizService {
    private static final String EOL = System.lineSeparator();

    private final QuizDao quizDao;

    private final IOService ioService;

    public QuizServiceImpl(QuizDao quizDao, IOService ioService) {
        this.quizDao = quizDao;
        this.ioService = ioService;
    }

    @Override
    public void printQuiz() {
        ioService.outputString("Russian Literature Quiz");
        List<Quiz>quizList;
        try {
            quizList = quizDao.getQuizData();
        } catch (QuizFileException e) {
            ioService.outputString(e.getMessage());
            return;
        }

        int i = 1;
        for (Quiz quiz : quizList) {
            ioService.outputString(formatQuestion(quiz, i++));
        }
    }

    private String formatChoice(String choice, int idx) {
        return Character.toString('A' + idx) + "." + choice;
    }

    private String formatQuestion(Quiz quiz, int idx) {
        StringBuilder questionSB = new StringBuilder();
        questionSB.append(idx).append(".").append(quiz.getQuestion()).append(EOL);
        questionSB.append("Choose the correct:").append(EOL);
        for (int i = 0; i < quiz.getAnswerList().length; i++) {
            questionSB.append("\t").append(formatChoice(quiz.getAnswerList()[i], i)).append(EOL);
        }
        questionSB.append("Tip: ").append(quiz.getCorrect().getComment()).append(EOL);
        int correctIdx = quiz.getCorrect().getAnswer() - 1;
        questionSB.append("Correct choice is: ").append(formatChoice(quiz.getAnswerList()[correctIdx], correctIdx));

        return questionSB.toString();
    }
}