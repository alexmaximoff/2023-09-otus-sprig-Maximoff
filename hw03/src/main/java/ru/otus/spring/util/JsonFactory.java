package ru.otus.spring.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class JsonFactory {
    private final ObjectMapper objectMapper;

    public JsonFactory() {
        this.objectMapper  = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // StdDateFormat is ISO8601 since jackson 2.9
                .setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}