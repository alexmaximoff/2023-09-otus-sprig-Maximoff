package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public class StudentServiceImpl implements StudentService {
    private final IOService ioService;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student greatStudent() {
        String firstName;
        String lastName;
        firstName = ioService.readStringWithPrompt("Enter first name:");
        lastName = ioService.readStringWithPrompt("Enter last name:");
        return new Student(firstName, lastName);
    }
}
