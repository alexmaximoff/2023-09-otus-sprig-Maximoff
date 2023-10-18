package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Quiz;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(AppConfig.class)
public class QuizServiceIntegrationTest {

    private QuizDao quizDao;

    @Autowired
    QuizServiceIntegrationTest(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Test
    public void getQuizData() {
        List<Quiz> quizList = quizDao.getQuizData();
        assertThat(quizList).isNotNull();
        assertThat(quizList.size()).isEqualTo(5);
    }
}
