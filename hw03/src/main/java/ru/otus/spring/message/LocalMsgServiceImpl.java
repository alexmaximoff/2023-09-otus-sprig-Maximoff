package ru.otus.spring.message;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.LocaleConfig;

@Service
@AllArgsConstructor
public class LocalMsgServiceImpl implements LocalMsgService {

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    public String getMsgByCode(String msgCode) {
        return messageSource.getMessage(msgCode, null, null, localeConfig.getLocale());
    }
}