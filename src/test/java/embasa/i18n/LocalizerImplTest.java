package embasa.i18n;

import embasa.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class LocalizerImplTest {

    @Autowired
    Localizer localizer;

    @Test
    public void testLocalization() {
        String messageUA = localizer.getMessage("label.test", LocaleUtil.LOCALE_UA);
        String messageRU = localizer.getMessage("label.test", LocaleUtil.LOCALE_RU);
        String messageEN = localizer.getMessage("label.test", Locale.ENGLISH);

        assertEquals("Тестове повідомлення", messageUA);
        assertEquals("Тестовое сообщение", messageRU);
        assertEquals("Test message", messageEN);

        LocaleContextHolder.setLocale(LocaleUtil.LOCALE_UA);
        assertEquals(messageUA, localizer.getMessage("label.test"));
        LocaleContextHolder.setLocale(LocaleUtil.LOCALE_RU);
        assertEquals(messageRU, localizer.getMessage("label.test"));
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertEquals(messageEN, localizer.getMessage("label.test"));
    }
}