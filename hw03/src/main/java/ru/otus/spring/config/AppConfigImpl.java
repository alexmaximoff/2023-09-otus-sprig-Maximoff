package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "quiz")
public class AppConfigImpl implements LocaleConfig, FileServiceConfig, ResultConfig {
    private int rightAnswersCountToPass;

    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    public void setRightAnswersCountToPass(int rightAnswersCountToPass) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setFileNameByLocaleTag(Map<String, String> fileNameByLocaleTag) {
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getQuizFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "rightAnswersCountToPass=" + rightAnswersCountToPass +
                ", locale=" + locale.toLanguageTag() +
                ", fileNameByLocaleTag=" + fileNameByLocaleTag +
                ", getQuizFileName=" + getQuizFileName() +
                '}';

    }
}
