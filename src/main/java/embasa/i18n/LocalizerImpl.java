package embasa.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/** Реалізація локалізації. */
public class LocalizerImpl implements Localizer {

    /** Отримувач повідомлень. */
    private final MessageSource messageSource;

    @Autowired
    public LocalizerImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(key, locale);
    }

    @Override
    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }
}
