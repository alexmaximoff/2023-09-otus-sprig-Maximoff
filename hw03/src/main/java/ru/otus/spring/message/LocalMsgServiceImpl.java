package ru.otus.spring.message;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppConfigImpl;

@Service
@AllArgsConstructor
public class LocalMsgServiceImpl implements LocalMsgService {

    private final MessageSource messageSource;

    private final AppConfigImpl appConfigImpl;

    public String getMsgByCode(String msgCode) {
        return messageSource.getMessage(msgCode, null, null, appConfigImpl.getLocale());
    }
}