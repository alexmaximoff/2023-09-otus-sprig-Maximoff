package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final IOService ioService;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student greatStudent() {
        String firstName = ioService.readStringWithPrompt("Enter first name:");
        String lastName = ioService.readStringWithPrompt("Enter last name:");
        return new Student(firstName, lastName);
    }
}
