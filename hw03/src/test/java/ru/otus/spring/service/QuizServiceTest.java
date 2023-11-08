package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuizDaoImpl;
import ru.otus.spring.domain.quiz.QuestionChoice;
import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.message.LocalMsgService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

@DisplayName("Класс QuizService")
@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizDaoImpl quizDao;

    @Mock
    private IOServiceStreams ioService;

    @Mock
    private StudentService studentService;

    @Mock
    private QuizResultService quizResultService;

    @Mock
    private LocalMsgService localMsgService;

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
        List<QuestionChoice> choiceList = new ArrayList<>();
        choiceList.add(new QuestionChoice("Choice A", "Choice A is correct.", true));
        choiceList.add(new QuestionChoice("Choice B", null, false));
        QuizQestion quizQestion = new QuizQestion("Test question?", choiceList);
        List<QuizQestion> quizQuestionList = new ArrayList<>();
        quizQuestionList.add(quizQestion);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(bo);

        given(quizDao.getQuizData())
                .willReturn(quizQuestionList);

        doAnswer(invocation -> {
            printStream.println((String)invocation.getArgument(0));
            return null;
        }).when(ioService).outputString(anyString());

        doAnswer(invocationOnMock -> null).when(quizResultService).applyAnswer(any(QuizQestion.class));
        doAnswer(invocationOnMock -> null).when(quizResultService).printResult(null, 0);
        given(localMsgService.getMsgByCode("QuizName"))
                .willReturn("Russian Literature Quiz");
        doAnswer(invocationOnMock -> null).when(studentService).greatStudent();

        service.runQuiz();

        bo.flush();
        String allWrittenLines = new String(bo.toByteArray());

        assertThat(allWrittenLines.stripTrailing()).isEqualTo(formattedQuizTextBlock.replace("\n",EOL));
    }
}
