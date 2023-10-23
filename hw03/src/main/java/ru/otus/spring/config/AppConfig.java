package ru.otus.spring.config;

import java.util.Locale;

public interface AppConfig {
    int getRightAnswersCountToPass();

    String getQuizFileName();

    Locale getLocale();
}
