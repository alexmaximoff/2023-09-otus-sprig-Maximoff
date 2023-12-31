package ru.otus.spring.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.CorrectAnswer;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.exceptions.QuizFileException;
import ru.otus.spring.util.FileResourcesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private final String quizDataFile;

    private final FileResourcesUtil fileResourcesUtil;

    private final ObjectMapper objectMapper;

    public QuizDaoImpl(String quizDataFile, FileResourcesUtil fileResourcesUtil, ObjectMapper objectMapper) {
        this.quizDataFile = quizDataFile;
        this.fileResourcesUtil = fileResourcesUtil;
        this.objectMapper = objectMapper;
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
                try {
                    quiz.add(parseQuizJson(x[0]));
                } catch (JsonProcessingException e) {
                    throw (new QuizFileException("Error occurred while parsing JSON from file" + quizDataFile));
                }
            });
        } catch (IOException | CsvException e) {
            throw(new QuizFileException("Error occurred while reading file " + quizDataFile));
        }
        return quiz;
    }

    private Quiz parseQuizJson(String jsonData) throws JsonProcessingException {
        Quiz quiz;
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        quiz = new Quiz();
        quiz.setQuestion(jsonNode.get("question").asText());
        quiz.setAnswerList(objectMapper.readValue(
                jsonNode.get("answerlist").toString(), String[].class)
        );
        quiz.setCorrect(
                new CorrectAnswer(
                        jsonNode.get("correct").get("answer").asInt(),
                        jsonNode.get("correct").get("comment").asText()
                )
        );

        return quiz;
    }
}
