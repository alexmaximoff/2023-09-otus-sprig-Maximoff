package ru.otus.spring.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {
    private final PrintStream output;

    private final Scanner scanner;

    public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
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
