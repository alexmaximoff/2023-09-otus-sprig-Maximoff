package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuizDaoImpl;
import ru.otus.spring.service.IOServiceStreams;
import ru.otus.spring.service.QuizServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

@DisplayName("Класс QuizTest")
@ExtendWith(MockitoExtension.class)
public class QuizTest {

    @Mock
    private QuizDaoImpl quizDao;

    @Mock
    private IOServiceStreams ioService;

    @InjectMocks
    private QuizServiceImpl service;

    @DisplayName("проверяет форматирование вопроса")
    @Test
    void shouldWellFormatQuestion() throws IOException {
        final String EOL = System.lineSeparator();
        //логика сервиса заключена в форматировании согласно требованиям бизнеса вопросов квиза
        StringBuilder formatedQuiz = new StringBuilder();
        formatedQuiz.append("Russian Literature Quiz").append(EOL);
        formatedQuiz.append("1.Test question?").append(EOL);
        formatedQuiz.append("Choose the correct:").append(EOL);
        formatedQuiz.append("\tA.Choice A").append(EOL);
        formatedQuiz.append("\tB.Choice B").append(EOL);
        formatedQuiz.append("Tip: Choice A is correct.").append(EOL);
        formatedQuiz.append("Correct choice is: A.Choice A");

        //исходный mock-объект с вопросом квиза
        Quiz quiz = new Quiz();
        quiz.setQuestion("Test question?");
        quiz.setAnswerList(new String[]{"Choice A", "Choice B"});
        quiz.setCorrect(new CorrectAnswer(1,"Choice A is correct."));
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);

        //Redirect System.out to buffer
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));

        given(quizDao.getQuizData())
                .willReturn(quizList);

        doAnswer(invocation -> {
            System.out.println((String)invocation.getArgument(0));
            return null;
        }).when(ioService).outputString(anyString());

        service.runQuiz();

        bo.flush();
        String allWrittenLines = new String(bo.toByteArray());

        assertThat(allWrittenLines.stripTrailing()).isEqualTo(formatedQuiz.toString());
    }
}
