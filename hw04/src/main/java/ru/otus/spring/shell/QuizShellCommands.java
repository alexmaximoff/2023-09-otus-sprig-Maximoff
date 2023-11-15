package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.config.AppConfigImpl;
import ru.otus.spring.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {
    private final QuizService quizService;

    private final AppConfigImpl appConfigImpl;

    @ShellMethod(value = "Run Quiz", key = {"r", "run"})
    public void runQuiz() {
        quizService.runQuiz();
    }
}
