package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuizService;

public class Main {

    public static void main(String[] args) {
        // TODO: создайте здесь класс контекста
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        QuizService quizService = context.getBean("quizService", QuizService.class);
        quizService.printQuiz();
    }
}
