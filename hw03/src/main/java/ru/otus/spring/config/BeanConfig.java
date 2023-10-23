package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.dao.QuizDaoImpl;
import ru.otus.spring.message.LocalMsgService;
import ru.otus.spring.service.*;
import ru.otus.spring.util.FileResourcesUtil;
import ru.otus.spring.util.JsonFactory;

@Configuration
public class BeanConfig {
    @Bean
    AppConfig appConfig () {
        return new AppConfigImpl();
    }

    @Bean
    JsonFactory jsonFactory() {
        return new JsonFactory();
    }

    @Bean
    FileResourcesUtil fileResourcesUtil() {
        return new FileResourcesUtil();
    }

    @Bean
    QuizDao quizDao (AppConfig appConfig, FileResourcesUtil fileResourcesUtil, JsonFactory jsonFactory) {
        return new QuizDaoImpl(appConfig, fileResourcesUtil, jsonFactory);
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
    QuizResult quizResult(AppConfig appConfig, IOService ioService) {
        return new QuizResultImpl(appConfig, ioService);
    }

    @Bean
    QuizService quizService(QuizDao quizDao,
                            IOService ioService,
                            StudentService studentService,
                            LocalMsgService localMsgService,
                            QuizResult quizResult) {
        return new QuizServiceImpl(quizDao, ioService, studentService, localMsgService, quizResult);
    }
}
