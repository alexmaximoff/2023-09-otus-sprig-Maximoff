package ru.otus.spring;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.config.AppConfigImpl;

@SpringBootApplication
@EnableConfigurationProperties(AppConfigImpl.class)
@AllArgsConstructor
public class Main  {
    public static void main(String... args)  {
        SpringApplication.run(Main.class, args);
    }
}
