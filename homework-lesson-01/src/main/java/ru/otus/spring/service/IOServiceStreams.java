package ru.otus.spring.service;

import java.io.PrintStream;

public class IOServiceStreams implements IOService {
    private final PrintStream output;

    public IOServiceStreams(PrintStream outputStream) {
        output = outputStream;
    }

    @Override
    public void outputString(String s) {
        output.print(s);
    }
}
