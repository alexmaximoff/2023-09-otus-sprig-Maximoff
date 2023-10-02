package ru.otus.spring.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.CorrectAnswer;
import ru.otus.spring.util.FileResourcesUtil;
import ru.otus.spring.util.JsonFactory;
import ru.otus.spring.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private final String quizDataFile;

    private final FileResourcesUtil fileResourcesUtil;

    public QuizDaoImpl(String quizDataFile, FileResourcesUtil fileResourcesUtil) {
        this.quizDataFile = quizDataFile;
        this.fileResourcesUtil = fileResourcesUtil;
    }

    @Override
    public List<Quiz> getQuizData() {
        InputStream inputStream = fileResourcesUtil.getFileFromResourceAsStream(quizDataFile);
        List<Quiz> quiz = new ArrayList<>();

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader);
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(
                     new CSVParserBuilder().withEscapeChar('\\').withQuoteChar('\"').build()).build()
        ) {
            //Строки и столбцы csv-таблицы
            List<String[]> r = csvReader.readAll();
            //в каждой строке и первый столбец
            r.forEach(x -> {
                quiz.add(parseQuizJson(x[0]));
            });
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return quiz;
    }

    private Quiz parseQuizJson(String jsonData) {
        Quiz quiz;
        try {
            JsonNode jsonNode = JsonFactory.OBJECT_MAPPER.readTree(jsonData);
            quiz = new Quiz();
            quiz.setQuestion(jsonNode.get("question").asText());
            quiz.setAnswerList(JsonFactory.OBJECT_MAPPER.readValue(
                    jsonNode.get("answerlist").toString(), String[].class)
            );
            quiz.setCorrect(
                    new CorrectAnswer(
                            jsonNode.get("correct").get("answer").asInt(),
                            jsonNode.get("correct").get("comment").asText()
                    )
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            quiz = null;
        }

        return quiz;
    }
}
