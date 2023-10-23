package ru.otus.spring;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.config.AppConfigImpl;
import ru.otus.spring.service.QuizService;

@SpringBootApplication
@EnableConfigurationProperties
@AllArgsConstructor
public class Main implements CommandLineRunner {

    private final QuizService quizService;

    private final AppConfigImpl appConfigImpl;


    public static void main(String... args)  {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(appConfigImpl.toString());
        quizService.runQuiz();
    }
}
