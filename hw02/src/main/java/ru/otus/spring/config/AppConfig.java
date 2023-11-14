package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.dao.QuizDaoImpl;
import ru.otus.spring.service.*;
import ru.otus.spring.util.FileResourcesUtil;
import ru.otus.spring.util.JsonFactory;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig implements TestConfig, TestFileNameProvider {

    // внедрить свойство из application.properties
    @Value("${quiz.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    // внедрить свойство из application.properties
    @Value("${quiz.fileName}")
    private String fileName;

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getQuizFileName() {
        return fileName;
    }

    @Bean
    FileResourcesUtil fileResourcesUtil() {
        return new FileResourcesUtil();
    }

    @Bean
    QuizDao quizDao (FileResourcesUtil fileResourcesUtil) {
        return new QuizDaoImpl(getQuizFileName(), fileResourcesUtil, JsonFactory.OBJECT_MAPPER);
    }

    @Bean
    IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @Bean
    StudentService studentService(IOService ioService) {
        return new StudentServiceImpl(ioService);
    }

    @Bean
    QuizResult quizResult(IOService ioService) {
        return new QuizResultImpl(ioService, getRightAnswersCountToPass());
    }

    @Bean
    QuizService quizService(QuizDao quizDao,
                            IOService ioService,
                            StudentService studentService,
                            QuizResult quizResult) {
        return new QuizServiceImpl(quizDao, ioService, studentService, quizResult);
    }
}
