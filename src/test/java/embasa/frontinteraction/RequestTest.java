package embasa.frontinteraction;

import embasa.LangUtil;
import embasa.i18n.LanguageHolder;
import embasa.i18n.LocaleUtil;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    Request request = LangUtil.buildRequest();

    @Test
    public void getLocale() {
        request.setLang("ua");
        assertEquals(LocaleUtil.LOCALE_UA, request.getLanguage().getLocale());
        request.setLang("ru");
        assertEquals(LocaleUtil.LOCALE_RU, request.getLanguage().getLocale());
        request.setLang("en");
        assertEquals(Locale.ENGLISH, request.getLanguage().getLocale());
    }

    @Test
    public void test() {
        String clientVersion = "111.222.333";
        String langCode = LangUtil.LANG_UA.getLangCode();
        request.setClientVersion(clientVersion);
        request.setLang(langCode);
        assertEquals(clientVersion, request.getClientVersion());
        assertEquals(langCode, request.getLang());
        assertEquals(LangUtil.LANG_UA, request.getLanguage());
    }
}