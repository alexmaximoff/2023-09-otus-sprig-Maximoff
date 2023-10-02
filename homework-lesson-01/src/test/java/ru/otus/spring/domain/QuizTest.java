package ru.otus.spring.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("Класс QuizTest")
public class QuizTest {
    @DisplayName("корректно создает экземпляр класса")
    @Test
    void shouldHaveCorrectInstance(){
        Quiz quiz = new Quiz();
        quiz.setQuestion("Test question?");
        quiz.setAnswerList(new String[]{"Choice A", "Choice B"});
        quiz.setCorrect(new CorrectAnswer(1,"Choice A is correct."));

        Assertions.assertEquals("Test question?", quiz.getQuestion());
        Assertions.assertTrue(Arrays.equals(new String[]{"Choice A", "Choice B"}, quiz.getAnswerList()));
    }
}
