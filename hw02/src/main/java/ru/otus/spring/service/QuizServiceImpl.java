package ru.otus.spring.service;

import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;
import ru.otus.spring.exceptions.QuizFileException;

import java.util.List;

public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    private final IOService ioService;

    private final StudentService studentService;

    private final QuizResult quizResult;

    private List<Quiz>quizList;

    public QuizServiceImpl(QuizDao quizDao, IOService ioService, StudentService studentService, QuizResult quizResult) {
        this.quizDao = quizDao;
        this.ioService = ioService;
        this.studentService = studentService;
        this.quizResult = quizResult;
    }

    @Override
    public void runQuiz() {
        loadQuizData();

        ioService.outputString("Russian Literature Quiz");
        Student student = studentService.greatStudent();

        int i = 1;
        for (Quiz quiz : quizList) {
            //напечатать вопрос
            ioService.outputString(formatQuestion(quiz, i++));
            //прочитать ответ
            quizResult.applyAnswer(quiz);
            //Напечатать ответ и комментарй
            ioService.outputString(formatQuestionTip(quiz));
        }

        //вывести результат для студента
        quizResult.printResult(student);
    }

    private void loadQuizData() {
        try {
            this.quizList = quizDao.getQuizData();
        } catch (QuizFileException e) {
            ioService.outputString(e.getMessage());
        }
    }

    private String formatChoice(String choice, int idx) {
        return Character.toString('A' + idx) + "." + choice;
    }

    private String formatQuestion(Quiz quiz, int idx) {
        final String eol = System.lineSeparator();
        StringBuilder questionSB = new StringBuilder();
        questionSB.append(idx).append(".").append(quiz.getQuestion()).append(eol);
        questionSB.append("Choose the correct:").append(eol);
        for (int i = 0; i < quiz.getAnswerList().length; i++) {
            questionSB.append("\t").append(formatChoice(quiz.getAnswerList()[i], i)).append(eol);
        }

        return questionSB.toString();
    }

    private String formatQuestionTip(Quiz quiz) {
        final String eol = System.lineSeparator();
        StringBuilder questionSB = new StringBuilder();
        questionSB.append("Tip: ").append(quiz.getCorrect().getComment()).append(eol);
        int correctIdx = quiz.getCorrect().getAnswer() - 1;
        questionSB.append("Correct choice is: ").
                append(formatChoice(quiz.getAnswerList()[correctIdx], correctIdx)).
                append(eol);
        return questionSB.toString();
    }
}