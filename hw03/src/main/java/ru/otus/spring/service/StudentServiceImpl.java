package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.message.LocalMsgService;

@Service
public class StudentServiceImpl implements StudentService {
    private final IOService ioService;

    private final LocalMsgService localMsgService;

    public StudentServiceImpl(IOService ioService, LocalMsgService localMsgService) {
        this.ioService = ioService;
        this.localMsgService = localMsgService;
    }

    @Override
    public Student greatStudent() {
        String firstName = ioService.readStringWithPrompt(localMsgService.getMsgByCode("StudentSrvFirstName"));
        String lastName = ioService.readStringWithPrompt(localMsgService.getMsgByCode("StudentSrvLastName"));
        return new Student(firstName, lastName);
    }
}
