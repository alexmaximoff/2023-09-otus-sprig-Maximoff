package ru.otus.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.Main;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Quiz;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = Main.class)
@DisplayName("Интеграционный тест")
public class QuizServiceIntegrationTest {

    @Autowired
    private QuizDao quizDao;

    @MockBean
    private QuizService quizService;

    @Test
    public void getQuizData() throws Throwable {
        Mockito.doNothing().when(quizService).runQuiz();

        List<Quiz> quizList = quizDao.getQuizData();
        assertThat(quizList).isNotNull();
        assertThat(quizList.size()).isEqualTo(5);
    }
}
