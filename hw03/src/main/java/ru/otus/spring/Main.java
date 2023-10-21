package ru.otus.spring;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.service.QuizService;

@SpringBootApplication
@AllArgsConstructor
public class Main implements CommandLineRunner {

    private final QuizService quizService;

    public static void main(String... args)  {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        quizService.runQuiz();
    }
}
