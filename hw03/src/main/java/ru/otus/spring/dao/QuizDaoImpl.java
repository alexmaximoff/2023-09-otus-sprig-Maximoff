package ru.otus.spring.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.FileServiceConfig;
import ru.otus.spring.domain.quiz.QuestionChoice;
import ru.otus.spring.domain.quiz.QuizQestion;
import ru.otus.spring.exceptions.QuizReadException;
import ru.otus.spring.util.FileResourcesUtil;
import ru.otus.spring.util.JsonFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizDaoImpl implements QuizDao {

    private final String quizDataFile;

    private final FileServiceConfig appConfig;

    private final FileResourcesUtil fileResourcesUtil;

    private final ObjectMapper objectMapper;

    public QuizDaoImpl(FileServiceConfig appConfig, FileResourcesUtil fileResourcesUtil, JsonFactory jsonFactory) {
        this.appConfig = appConfig;
        this.fileResourcesUtil = fileResourcesUtil;
        this.objectMapper = jsonFactory.getObjectMapper();
        this.quizDataFile = this.appConfig.getQuizFileName();
    }

    @Override
    public List<QuizQestion> getQuizData() throws QuizReadException {
        InputStream inputStream = fileResourcesUtil.getFileFromResourceAsStream(appConfig.getQuizFileName());
        List<QuizQestion> quizQestions = new ArrayList<>();

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader);
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(
                     new CSVParserBuilder().withEscapeChar('\\').withQuoteChar('\'').build()).build()
        ) {
            //Строки и столбцы csv-таблицы
            List<String[]> r = csvReader.readAll();
            //в каждой строке и первый столбец
            r.forEach(x -> {
                try {
                    quizQestions.add(parseQuizJson(x[0]));
                } catch (JsonProcessingException e) {
                    throw(new QuizReadException("Error occurred while parsing json " + quizDataFile, e));
                }
            });
        } catch (IOException | CsvException e) {
            throw(new QuizReadException("Error occurred while reading file " + quizDataFile, e));
        }
        return quizQestions;
    }

    private QuizQestion parseQuizJson(String jsonData) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        //получим коллекцию с вариантами ответов
        String[] answerlist = objectMapper.readValue(
                jsonNode.get("answerlist").toString(), String[].class);
        //получим номер и комментарий правильного ответа
        int correctChoice = jsonNode.get("correct").get("answer").asInt();
        String comment = jsonNode.get("correct").get("comment").asText();
        //сформируем варианты ответов с указанием корректного и комментрием
        List<QuestionChoice> questionChoices = new ArrayList<>();
        for (int i = 0; i < answerlist.length; i++) {
            questionChoices.add(
                    new QuestionChoice(answerlist[i], i == correctChoice - 1 ? comment : null, i == correctChoice - 1));
        }
        QuizQestion quizQestion = new QuizQestion(jsonNode.get("question").asText(), questionChoices);
        return quizQestion;
    }
}
