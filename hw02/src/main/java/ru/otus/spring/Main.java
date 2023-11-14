package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.QuizService;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        //Создать контекст на основе Annotation/Java конфигурирования
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        var quizService = context.getBean(QuizService.class);
        quizService.runQuiz();
    }
}
