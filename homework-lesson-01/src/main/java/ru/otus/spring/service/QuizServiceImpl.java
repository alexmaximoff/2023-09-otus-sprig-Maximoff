package ru.otus.spring.service;

import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Quiz;

import java.util.List;

public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    private String formatChoice(String choice, int idx) {
        return Character.toString('A' + idx) + "." + choice;
    }

    private String formatQuestion(Quiz quiz, int idx) {
        StringBuilder quizSB = new StringBuilder();
        quizSB.append(idx).append(".").append(quiz.getQuestion()).append("\n");
        quizSB.append("Choose the correct:\n");
        for (int i = 0; i < quiz.getAnswerList().length; i++) {
            quizSB.append("\t ").append(formatChoice(quiz.getAnswerList()[i], i)).append("\n");
        }
        quizSB.append("Tip: ").append(quiz.getCorrect().getComment()).append("\n");
        int correctIdx = quiz.getCorrect().getAnswer() - 1;
        quizSB.append("Correct choice is: ").append(formatChoice(quiz.getAnswerList()[correctIdx], correctIdx));

        return quizSB.toString();
    }

    @Override
    public void printQuiz() {
        List<Quiz>quizList = quizDao.getQuizData();
        System.out.println("Russian Literature Quiz");
        int i = 1;
        for (Quiz quiz : quizList) {
            System.out.println(formatQuestion(quiz, i++));
            System.out.println();
        }
    }
}
