package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements IOService {
    private final PrintStream output;

    private final Scanner scanner;

    public IOServiceStreams(@Value("#{T(System).out}") PrintStream printStream,
                            @Value("#{T(System).in}") InputStream inputStream) {
        output = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        output.print(prompt);
        return scanner.nextLine();
    }
}
