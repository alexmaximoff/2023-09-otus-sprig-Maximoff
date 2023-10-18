package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuizDaoImpl;
import ru.otus.spring.domain.CorrectAnswer;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

@DisplayName("Класс QuizTest")
@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizDaoImpl quizDao;

    @Mock
    private IOServiceStreams ioService;

    @Mock
    private StudentService studentService;

    @Mock
    private QuizResult quizResult;

    @InjectMocks
    private QuizServiceImpl service;

    @DisplayName("проверяет форматирование вопроса")
    @Test
    void shouldWellFormatQuestion() throws IOException {
        final String EOL = System.lineSeparator();
        //логика сервиса заключена в форматировании согласно требованиям бизнеса вопросов квиза
        String formattedQuizTextBlock = """
                Russian Literature Quiz
                1.Test question?
                Choose the correct:
                \tA.Choice A
                \tB.Choice B
                
                Tip: Choice A is correct.
                Correct choice is: A.Choice A""";

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

        doAnswer(invocationOnMock -> null).when(quizResult).applyAnswer(quiz);
        doAnswer(invocationOnMock -> null).when(quizResult).printResult(null);
        doAnswer(invocationOnMock -> null).when(studentService).greatStudent();

        service.runQuiz();

        bo.flush();
        String allWrittenLines = new String(bo.toByteArray());

        assertThat(allWrittenLines.stripTrailing()).isEqualTo(formattedQuizTextBlock.replace("\n",EOL));
    }
}
