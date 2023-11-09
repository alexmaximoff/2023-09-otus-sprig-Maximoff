package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.quiz.QuizQestion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@DisplayName("Интеграционный тест")
public class QuizServiceIntegrationTest {

    @Autowired
    private QuizDao quizDao;

    @MockBean
    private QuizService quizService;

    @Test
    public void getQuizData() {
        Mockito.doNothing().when(quizService).runQuiz();

        List<QuizQestion> quizQuestionList = quizDao.getQuizData();
        assertThat(quizQuestionList).isNotNull();
        assertThat(quizQuestionList.size()).isEqualTo(5);
    }
}
